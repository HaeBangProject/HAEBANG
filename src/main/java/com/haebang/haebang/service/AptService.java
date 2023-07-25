package com.haebang.haebang.service;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.dto.AptItemReq;
import com.haebang.haebang.entity.Apt;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.entity.Member;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.repository.AptRepository;
import com.haebang.haebang.repository.ItemRepository;
import com.haebang.haebang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AptService {
    final MemberRepository memberRepository;
    final AptRepository aptRepository;
    final ItemRepository itemRepository;


    public Item createItem(Authentication authentication, AptItemReq req) {
        Apt apt = new Apt();
        if (req.getRoadAddress() == null) {// 주소가 있어야함
            System.out.println(req.getRoadAddress());
            throw new CustomException(CustomErrorCode.INVALID_FORMAT_ADDRESS);
        }

        Optional<Apt> optionalApt = aptRepository.findByRoadAddress(req.getRoadAddress());
        if(optionalApt.isPresent()){// 이미 저장된 apt 가 있을때
            apt = optionalApt.get();
            apt.increaseCnt();
        }else{// 새 아파트 등록할때
            apt.setRoadAddress(req.getRoadAddress());
            apt.setDp(req.getDp());
            apt.setCnt(1L);
            System.out.println("아파트 새로 저장"+apt.getRoadAddress()+" "+apt.getDp());
            aptRepository.save(apt);
        }
        Optional<Member> optionalMember = memberRepository.findByUsername(authentication.getName());
        Member member = new Member();
        if(optionalMember.isEmpty()){
            System.out.println(authentication.getName()+" : 빈 member 객체임");
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
        return item;
    }

    public Item findItem(Long idx){
        Item entity = itemRepository.findById(idx).orElseThrow();
        entity.setHits(entity.getHits()+1);
        itemRepository.save(entity);
        return entity;
    }
    public AptItemReq getItemForEdit(Long idx){
        Item item = itemRepository.findById(idx).orElseThrow();
        Apt apt = aptRepository.findById(item.getApt().getId()).orElseThrow();
        AptItemReq dto = new AptItemReq();
        dto.fromEntityToDto(apt, item);
        return dto;
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


    public boolean deleteItem(String username, Long idx){
        Item item = itemRepository.findById(idx).orElseThrow();
        if(!item.getUsername().equals(username)) throw new CustomException(CustomErrorCode.INVALID_EDIT_USER);
        itemRepository.deleteById(idx);
        item.getApt().decreaseCnt();
        aptRepository.save(item.getApt());
        return true;
    }

    public List<Apt> findAllApt() { return aptRepository.findAll();}

    public Optional<Apt> findByAptId(Long id) { return aptRepository.findById(id);}
    public Apt findAptByRoadAddress(String roadAddress) {
        return aptRepository.findByRoadAddress(roadAddress).orElseThrow();
    }


}
