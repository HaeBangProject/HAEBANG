package com.haebang.haebang.controller;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.dto.AptItemReq;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.service.AptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/apt")
public class AptController {
    final AptService aptService;

    @PostMapping("item")// 집 내놓기
    public ResponseEntity createAptItem(@RequestBody AptItemReq req,
                                        Authentication authentication){
        log.info("집 내놓기");

        System.out.println(req.toString());

        if(!authentication.isAuthenticated())
            throw new CustomException(CustomErrorCode.INVALID_MEMBER_INFO);
        return new ResponseEntity(aptService.createItem(authentication.getName() ,req), HttpStatus.OK);
    }

    @PutMapping("item/{id}")// 글 수정
    public ResponseEntity editAptItem(@PathVariable("id") Long id,
                                      @RequestBody AptItemReq req,
                                      Authentication authentication
    ){
        if(!authentication.isAuthenticated())
            throw new CustomException(CustomErrorCode.INVALID_MEMBER_INFO);

        return new ResponseEntity(aptService.updateItem(authentication.getName(), id, req), HttpStatus.OK);
    }

    @GetMapping("items")// 검색 조건 params
    public ResponseEntity getAptItems(@RequestParam Map<String, String> params
    ){
        if(params.isEmpty()){// param없으면
            return new ResponseEntity( aptService.findAllItems(), HttpStatus.OK);
        }
        // TODO: 아파트 정보 받아서 해당 아파트 매물 조회 하기로 바꿈
        List<Item> items = aptService.findItemsByRoadAddress(params.get("roadAddress"));
        if(items==null) return new ResponseEntity("동일한 주소의 매물이 없습니다", HttpStatus.OK);
        return new ResponseEntity(items, HttpStatus.OK);
    }

    @GetMapping("item/{id}")// 하나만 조회
    public ResponseEntity getAptItem(@PathVariable("id") Long id
    ){
        if(id==null) return new ResponseEntity("잘못된 매물 접근", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(aptService.findItem(id), HttpStatus.OK);
    }

    @DeleteMapping("item/{id}")// 삭제
    public ResponseEntity deleteApt(@PathVariable("id") Long id,
                                    Authentication authentication
    ){
        if(!aptService.deleteItem(authentication.getName(), id)){
            return new ResponseEntity("삭제 안됨", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("삭제되었습니다", HttpStatus.OK);
    }

}
