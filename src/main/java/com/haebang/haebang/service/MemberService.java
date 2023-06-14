package com.haebang.haebang.service;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.dto.JwtDto;
import com.haebang.haebang.dto.JoinDto;
import com.haebang.haebang.dto.LoginDto;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.repository.MemberRepository;
import com.haebang.haebang.entity.Member;
import com.haebang.haebang.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
                .role("ROLE_USER")
                .password(encoder.encode( password )).build());
        return "회원가입 성공";
    }

    public JwtDto login(LoginDto dto){
        Member member = memberRepository.findByUsername(dto.getUsername())
                .orElseThrow(()-> new CustomException(CustomErrorCode.INVALID_MEMBER_INFO) );// 존재하지 않는 사용자
        if( !encoder.matches(  dto.getPassword(), member.getPassword()) ){
            throw new CustomException(CustomErrorCode.INVALID_MEMBER_INFO);
        }

        String accessToken = jwtProvider.createToken(member, "ATK", 720L);
        String refreshToken = jwtProvider.createToken(member, "RFT", 720L);

        jwtProvider.setTokenValueAndTime(refreshToken, member.getEmail());

        return JwtDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .username(member.getUsername())
                .build();
    }
    //현재 로그인 유저
    public String login_user(JoinDto dto){
        System.out.println(memberRepository.findByUsername(dto.getUsername())+"hi");
        return String.valueOf(memberRepository.findByUsername(dto.getUsername()));
    }

    public String reissue(String refreshToken){
        String value = jwtProvider.getValueFromToken(refreshToken);
        String username = jwtProvider.getUsername(refreshToken);
        String email = jwtProvider.getEmail(refreshToken);

        Member member = (Member)jwtProvider.getUserDetails(refreshToken);

        Set<String> set = new HashSet<>();
        set.add("first");
        for(String str : set){

        }

        String accessToken = null;
        if(value.equals(email)){
            accessToken = jwtProvider.createToken(member, "ATK", 24L);
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
