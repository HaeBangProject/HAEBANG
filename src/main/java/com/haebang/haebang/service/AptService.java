package com.haebang.haebang.service;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.dto.AptItemReq;
import com.haebang.haebang.entity.Apt;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.repository.AptRepository;
import com.haebang.haebang.repository.ItemRepository;
import com.haebang.haebang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AptService {
    final MemberRepository memberRepository;
    final AptRepository aptRepository;
    final ItemRepository itemRepository;

    public Item createItem(String username, AptItemReq req) {
        Apt apt = new Apt();
        if (req.getRoadAddress() == null || req.getRoadAddress().split(" ").length!=2) {// 주소가 있어야하고, 포멧에 맞아야 함
            throw new CustomException(CustomErrorCode.INVALID_FORMAT_ADDRESS);
        }

        Optional<Apt> optional = aptRepository.findByRoadAddress(req.getRoadAddress());
        if(optional.isPresent()){// 이미 저장된 apt 가 있을때
            apt = optional.get();
        }else{// 새 아파트 등록할때
            apt.setRoadAddress(req.getRoadAddress());
            apt.setDp(req.getDp());
            System.out.println("아파트 새로 저장"+apt.getRoadAddress()+" "+apt.getDp());
            aptRepository.save(apt);
        }

        Item item = Item.builder()
                .apt(apt)
                .username(username)
                .phoneNumber(req.getPhoneNumber())
                .hits(0L)
                .text(req.getText())
                .title(req.getTitle())
                .price(req.getPrice())
                .dong(req.getDong())
                .floor(req.getFloor())
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

    public Item updateItem(String username, Long idx, AptItemReq req){
        // 아파트 정보는 수정 x
        Item item = itemRepository.findById(idx).orElseThrow();
        if(!item.getUsername().equals(username)) throw new CustomException(CustomErrorCode.INVALID_EDIT_USER);

        item.update(req);
        itemRepository.save(item);
        return item;
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
        itemRepository.delete(item);
        return true;
    }
}
