package com.haebang.haebang.service;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.dto.JwtDto;
import com.haebang.haebang.dto.MemberReqDto;
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

    public String join(String username, String password, String email){
        if( memberRepository.findByUsername(username).isPresent() ) {
            throw new CustomException(CustomErrorCode.ALREADY_JOINED_MEMBER);
        }
        memberRepository.save(Member.builder()
                .username(username)
                .email(email)
                .password(encoder.encode( password )).build());
        return "회원가입 성공";
    }

    public JwtDto login(MemberReqDto dto){
        Member member = memberRepository.findByUsername(dto.getUsername())
                .orElseThrow(()-> new CustomException(CustomErrorCode.INVALID_MEMBER_INFO) );// 존재하지 않는 사용자
        if( !encoder.matches(  dto.getPassword(), member.getPassword()) ){
            throw new CustomException(CustomErrorCode.INVALID_MEMBER_INFO);
        }
        if(!dto.getEmail().equals(member.getEmail())){
            throw new CustomException(CustomErrorCode.INVALID_MEMBER_INFO);
        }

        String accessToken = jwtProvider.createToken(dto, "ATK", 24L);
        String refreshToken = jwtProvider.createToken(dto, "RFT", 720L);

        jwtProvider.setTokenValueAndTime(refreshToken, dto.getEmail());

        return JwtDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }
    //현재 로그인 유저
    public String login_user(MemberReqDto dto){
        System.out.println(memberRepository.findByUsername(dto.getUsername())+"hi");
        return String.valueOf(memberRepository.findByUsername(dto.getUsername()));
    }

    public String reissue(String refreshToken){
        String value = jwtProvider.getValueFromToken(refreshToken);
        String username = jwtProvider.getUsername(refreshToken);
        String email = jwtProvider.getEmail(refreshToken);

        MemberReqDto dto = MemberReqDto.builder()
                .username(username)
                .email(email)
                .build();

        String accessToken = null;
        if(value.equals(email)){
            accessToken = jwtProvider.createToken(dto, "ATK", 24L);
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
