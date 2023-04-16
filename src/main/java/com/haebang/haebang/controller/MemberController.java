package com.haebang.haebang.controller;

import com.haebang.haebang.dto.MemberReqDto;
import com.haebang.haebang.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/member")
public class MemberController {
    final private MemberService userService;

    // 회원 가입
    @PostMapping("join")
    public ResponseEntity<?> join(@RequestBody MemberReqDto joinReqDto){
        String text = userService.join(joinReqDto.getUsername(), joinReqDto.getPassword());
        return ResponseEntity.ok().body(text);
    }
    // 로그인
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody MemberReqDto dto){
        String token = userService.login(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok().body(token);
    }
    //인중
    @PostMapping("test")
    public ResponseEntity<?> test(Authentication authentication){
        log.info("jwt 로그인 성공 사용자: "+authentication.getName());
        return ResponseEntity.ok().body(authentication.getName()+"님 로그인이 완료되었습니다");
    }

    // 로그아윳
    @PostMapping("logout")
    public  void logout(){}
}
