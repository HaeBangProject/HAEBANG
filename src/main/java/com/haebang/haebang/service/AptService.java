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
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AptService {
    final MemberRepository memberRepository;
    final AptRepository aptRepository;
    final ItemRepository itemRepository;

    public Item createItem(String username, AptItemReq req) {
        Apt apt;
        if (req.getAptId() == null) {
            apt = new Apt();
            aptRepository.save(apt);
        } else {
            apt = aptRepository.findById(req.getAptId()).orElseThrow();
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

    public List<Item> findItemsByParams(){
        return itemRepository.findAll();
    }

    public boolean deleteItem(String username, Long idx){
        Item item = itemRepository.findById(idx).orElseThrow();
        if(!item.getUsername().equals(username)) throw new CustomException(CustomErrorCode.INVALID_EDIT_USER);
        itemRepository.delete(item);
        return true;
    }
}
