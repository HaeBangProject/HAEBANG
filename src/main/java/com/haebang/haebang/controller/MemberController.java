package com.haebang.haebang.controller;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.dto.JwtDto;
import com.haebang.haebang.dto.JoinDto;
import com.haebang.haebang.dto.LoginDto;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<?> join(@Validated @RequestBody JoinDto joinReqDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            for(ObjectError error : bindingResult.getAllErrors()){
                FieldError fieldError = (FieldError) error;
                sb.append(fieldError.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        System.out.println(joinReqDto.toString());
        String text = memberService.join(joinReqDto.getUsername(), joinReqDto.getPassword(), joinReqDto.getEmail());
        log.info(text);
        return ResponseEntity.ok().body(text);
    }
    // 로그인
    @PostMapping("login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginDto dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            for(ObjectError error : bindingResult.getAllErrors()){
                FieldError fieldError = (FieldError) error;
                sb.append(fieldError.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        JwtDto token = memberService.login(dto);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,
                ResponseCookie.from("ATK", token.getAccessToken())
                        .maxAge(24*60*60*30)
                        .path("/")
                        .build()
                        .toString());
        headers.add(HttpHeaders.SET_COOKIE,
                ResponseCookie.from("RTK", token.getRefreshToken())
                .maxAge(24*60*60*30)
                        .path("/")
                        .build()
                        .toString());
        headers.add(HttpHeaders.SET_COOKIE,
                ResponseCookie.from("username", token.getUsername())
                .maxAge(24*60*60*30)
                        .path("/")
                        .build()
                        .toString());
        log.info("headers : {}", headers);
        return ResponseEntity.ok().headers(headers).body(token);
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
    @GetMapping("username")
    public ResponseEntity<?> getUsername(Authentication authentication){
        return ResponseEntity.ok().body(authentication.getName());
    }

    @GetMapping("reissue")
    public ResponseEntity<?> reissue(@CookieValue(name = "RTK", defaultValue = "")String rtk){
        // 리프레시 토큰 쿠키로 받아서 -> access token 새로 쿠키에 넣어주기
        String accessToken = memberService.reissue(rtk);
        if(accessToken==null) throw new CustomException(CustomErrorCode.RTK_REISSUE_ERROR);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, ResponseCookie.from("ATK", accessToken)
                .maxAge(24*60*60*30)
                .path("/")
                .build()
                .toString()).body(accessToken);
    }

    // 로그아윳
    @PostMapping("logout")
    public ResponseEntity<?> logout(JwtDto jwtDto){

        memberService.toBlackListed(jwtDto); //TODO: 배포할땐 주석해제하기, GETDEL 명령어 안먹어서 주석처리 해둠
        log.info("ATK 블랙리스트에 등록");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,
                ResponseCookie.from("ATK", null)
                        .maxAge(0)
                        .path("/")
                        .build()
                        .toString());
        headers.add(HttpHeaders.SET_COOKIE,
                ResponseCookie.from("RTK", null)
                        .maxAge(0)
                        .path("/")
                        .build()
                        .toString());
        headers.add(HttpHeaders.SET_COOKIE,
                ResponseCookie.from("username", null)
                        .maxAge(0)
                        .path("/")
                        .build()
                        .toString());

        log.info("headers : {}", headers);
        return ResponseEntity.ok().headers(headers)
                .body("로그아웃이 완료되었습니다");
    }
}
