package com.haebang.haebang.service;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.document.AptDocument;
import com.haebang.haebang.dto.AptItemReq;
import com.haebang.haebang.dto.AptItemRes;
import com.haebang.haebang.entity.Apt;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.entity.Member;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AptService {
    private final MemberRepository memberRepository;
    private final AptRepository aptRepository;
    private final ItemRepository itemRepository;
    private final AptSearchRepository aptSearchRepository;
    private final BookmarkRepository bookmarkRepository;

    public Item createItem(Authentication authentication, AptItemReq req) {
        Apt apt = new Apt();

        Optional<Apt> optionalApt = aptRepository.findByRoadAddress(req.getRoadAddress());
        if(optionalApt.isPresent()){// 이미 저장된 apt 가 있을때
            apt = optionalApt.get();
            apt.increaseCnt();
        }else{// 새 아파트 등록할때
            apt.setDp( req.getDp().replace("아파트", "") );
            apt.setRoadAddress(req.getRoadAddress());
            apt.setCnt(1L);
            apt = aptRepository.save(apt);
        }
        Optional<Member> optionalMember = memberRepository.findByUsername(authentication.getName());
        Member member = new Member();
        if(optionalMember.isEmpty()){
            throw new CustomException(CustomErrorCode.INVALID_MEMBER_INFO);
        }else{
            member = optionalMember.get();
        }

        Item item = Item.builder()
                .apt(apt)
                .username(authentication.getName())
                .member(member)
                .phoneNumber(req.getPhoneNumber())
                .hits(0L)
                .text(req.getText())
                .title(req.getTitle())
                .dp_amount(req.getDp_amount())
                .dp_area(Double.parseDouble( req.getDp_area() ))
                .build_year(req.getBuild_year())
                .dong(req.getDong())
                .floor(req.getFloor())
                .contract_date(req.getContract_date())
                .build();

        itemRepository.save(item);
        aptSearchRepository.save(AptDocument.from(apt));
        return item;
    }

    public Item findItem(Long idx){
        Item entity = itemRepository.findById(idx).orElseThrow();
        entity.setHits(entity.getHits()+1);
        itemRepository.save(entity);
        return entity;
    }
    public AptItemRes getItemForEdit(Long idx){// apt+item+images
        Item item = itemRepository.findById(idx).orElseThrow();
        AptItemReq req = new AptItemReq();
        req.fromEntityToDto(item.getApt(), item);

        AptItemRes response = new AptItemRes();
        response.setAptItemReq(req);
        response.setS3Files(item.getS3Files());

        return response;
    }

    public Item updateItem(String username, Long idx, AptItemReq req){
        // 아파트 정보는 수정 x
        Item item = itemRepository.findById(idx).orElseThrow();
        if(!item.getUsername().equals(username)) throw new CustomException(CustomErrorCode.INVALID_EDIT_USER);

        item.update(req);
        itemRepository.save(item);
        return item;
    }
    public Optional<Item> findItem2(Long id){
        return itemRepository.findByAptId(id);
    }
    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }
    public List<Item> findItemsByRoadAddress(String roadAddress){
        // apt 리포지토리에서 꺼내서 item list 반환
        Optional<Apt> entity = aptRepository.findByRoadAddress(roadAddress);
        if(entity.isEmpty()) return null;
        return entity.get().getItems();
    }
    public List<Item> findItemsByMember(Member member){
        return itemRepository.findAllByMember(member);
    }


    public boolean deleteItem(String username, Long idx){
        Item item = itemRepository.findById(idx).orElseThrow();
        // item삭제시 관련 북마크 삭제
//        bookmarkRepository.deleteAllByItem(item);

        if(!item.getUsername().equals(username)) throw new CustomException(CustomErrorCode.INVALID_EDIT_USER);
        itemRepository.deleteById(idx);
        item.getApt().decreaseCnt();
        item.getApt().decreaseCnt();
        if(item.getApt().getCnt() > 0){
            aptRepository.save(item.getApt());
        }else{
            // 더이상 존재하는 item 이없는 apt 일 경우 - db 애서 지우고, 검색에 안뜨게 elastic search 애서도 지우기
            aptRepository.delete(item.getApt());
            aptSearchRepository.delete(AptDocument.from(item.getApt()));
        }
        return true;
    }

    public List<Apt> findAllApt() { return aptRepository.findAll();}

    public Optional<Apt> findByAptId(Long id) { return aptRepository.findById(id);}
    public Apt findAptByRoadAddress(String roadAddress) {
        return aptRepository.findByRoadAddress(roadAddress).orElseThrow();
    }


}
