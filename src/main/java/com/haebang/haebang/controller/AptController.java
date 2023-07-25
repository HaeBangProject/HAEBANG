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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/apt")
public class AptController {
    final AptService aptService;

    @PostMapping("item")// 집 내놓기
    public ResponseEntity createAptItem(@Validated @RequestBody AptItemReq req, BindingResult bindingResult,
                                        Authentication authentication){
        log.info("집 내놓기");

        System.out.println(req.toString());

        if(!authentication.isAuthenticated()) throw new CustomException(CustomErrorCode.INVALID_MEMBER_INFO);

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            for(ObjectError error : bindingResult.getAllErrors()){
                FieldError fieldError = (FieldError) error;
                sb.append(fieldError.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }

        return new ResponseEntity(aptService.createItem(authentication ,req), HttpStatus.OK);
    }

    @PutMapping("item/{id}")// 글 수정
    public ResponseEntity editAptItem(@PathVariable("id") Long id,
                                      @Validated @RequestBody AptItemReq req, BindingResult bindingResult,
                                      Authentication authentication
    ){
        if(!authentication.isAuthenticated())
            throw new CustomException(CustomErrorCode.INVALID_MEMBER_INFO);

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            for(ObjectError error : bindingResult.getAllErrors()){
                FieldError fieldError = (FieldError) error;
                sb.append(fieldError.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }

        return new ResponseEntity(aptService.updateItem(authentication.getName(), id, req), HttpStatus.OK);
    }

    @GetMapping("items")// 도로명 주소에 따른 매물 검색
    public ResponseEntity getAptItems(@RequestParam Map<String, String> params
    ){
        if(params.isEmpty()){// param없으면
            return new ResponseEntity( aptService.findAllItems(), HttpStatus.OK);
        }

        List<Item> items = aptService.findItemsByRoadAddress(params.get("road_address"));
        if(items==null) return new ResponseEntity("동일한 주소의 매물이 없습니다", HttpStatus.OK);
        return new ResponseEntity(items, HttpStatus.OK);
    }

    @GetMapping("item/{id}")// 하나만 조회
    public ResponseEntity getAptItem(@PathVariable("id") Long id
    ){
        if(id==null) return new ResponseEntity("잘못된 매물 접근", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(aptService.findItem(id), HttpStatus.OK);
    }
    @GetMapping("item/edit/{id}")// 수정할 수 있는 정보들만 모아 dto 로 보내줌
    public ResponseEntity getItemDetail(@PathVariable("id") Long id){
        if(id==null) return new ResponseEntity("잘못된 매물 접근", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(aptService.getItemForEdit(id), HttpStatus.OK);
    }

    @DeleteMapping("item/{id}")// 삭제
    public ResponseEntity deleteApt(@PathVariable("id") Long id,
                                    Authentication authentication
    ){
        System.out.println(id+"아이템 삭제");
        if(!aptService.deleteItem(authentication.getName(), id)){
            return new ResponseEntity("삭제 안됨", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("삭제되었습니다", HttpStatus.OK);
    }

}
