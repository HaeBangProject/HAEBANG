package com.haebang.haebang.service;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.dto.JwtDto;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.repository.MemberRepository;
import com.haebang.haebang.entity.Member;
import com.haebang.haebang.utils.JwtProvider;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    final private JwtProvider jwtProvider;
    final private RedisService redisService;
    final private MemberRepository memberRepository;
    final private BCryptPasswordEncoder encoder;

    public String join(String username, String password){
        if( memberRepository.findByUsername(username).isPresent() ) {
            throw new CustomException(CustomErrorCode.ALREADY_JOINED_MEMBER);
        }
        memberRepository.save(Member.builder()
                .username(username)
                .password(encoder.encode( password )).build());
        return "회원가입 성공";
    }

    public JwtDto login(String username, String password){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new CustomException(CustomErrorCode.INVALID_MEMBER_INFO) );// 존재하지 않는 사용자
        if( !encoder.matches(  password, member.getPassword()) ){
            throw new CustomException(CustomErrorCode.INVALID_MEMBER_INFO);
        }
        String accessToken = jwtProvider.createToken(member.getUsername(), "ATK", 24L);
        String refreshToken = jwtProvider.createToken(member.getUsername(), "RFT", 720L);

        jwtProvider.setTokenValueAndTime(refreshToken, username);

        return JwtDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

    public String reissue(String refreshToken){
        String value = jwtProvider.getValueFromToken(refreshToken);
        String username = jwtProvider.getUsername(refreshToken);
        String accessToken = null;
        if(value.equals(username)){
            accessToken = jwtProvider.createToken(username, "ATK", 24L);
        }
        log.info(username+"님 재발급 : "+accessToken);
        return accessToken;
    }

    public void toBlackListed(JwtDto jwtDto){
        jwtProvider.setTokenValueAndTime(jwtDto.getAccessToken(), "logout");
        redisService.deleteKey(jwtDto.getRefreshToken());
        return;
    }
}
