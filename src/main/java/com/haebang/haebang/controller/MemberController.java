package com.haebang.haebang.controller;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.dto.JwtDto;
import com.haebang.haebang.dto.MemberReqDto;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.service.MemberService;
import com.haebang.haebang.utils.JwtProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/member")
public class MemberController {
    final private MemberService memberService;

    // 회원 가입
    @PostMapping("join")
    public ResponseEntity<?> join(@RequestBody MemberReqDto joinReqDto){
        String text = memberService.join(joinReqDto.getUsername(), joinReqDto.getPassword(), joinReqDto.getEmail());
        return ResponseEntity.ok().body(text);
    }
    // 로그인
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody MemberReqDto dto){
        JwtDto token = memberService.login(dto);
        return ResponseEntity.ok().body(token);
    }
    //인중
    @PostMapping("test")
    public ResponseEntity<?> test(Authentication authentication){
        log.info("jwt 로그인 성공 사용자: "+authentication.getName());
        log.info(authentication.getPrincipal().toString());
        log.info(authentication.getAuthorities().toString());
        log.info(authentication.getDetails().toString());
        return ResponseEntity.ok().body(authentication.getName()+"님 로그인이 완료되었습니다");
    }

    @GetMapping("reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request){
        String accessToken = memberService.reissue(
                request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1]
        );
        if(accessToken==null) throw new CustomException(CustomErrorCode.RTK_REISSUE_ERROR);
        return ResponseEntity.ok().body(accessToken);
    }

    // 로그아윳
    @PostMapping("logout")
    public ResponseEntity<?> logout(Authentication authentication, @RequestBody JwtDto jwtDto){
        log.info("ATK 블랙리스트에 등록");
        memberService.toBlackListed(jwtDto);
        return ResponseEntity.ok()
                .body("로그아웃이 완료되었습니다");
    }
}
