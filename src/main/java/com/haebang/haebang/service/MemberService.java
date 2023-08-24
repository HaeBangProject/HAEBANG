package com.haebang.haebang.service;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.dto.JwtDto;
import com.haebang.haebang.dto.LoginDto;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.repository.MemberRepository;
import com.haebang.haebang.entity.Member;
import com.haebang.haebang.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    @Value("${jwt.duration.atk}")
    private Long atkDuration;
    @Value("${jwt.duration.rtk}")
    private Long rtkDuration;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

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

        String accessToken = jwtProvider.createToken(member, "ATK", atkDuration);
        String refreshToken = jwtProvider.createToken(member, "RTK", rtkDuration);

        jwtProvider.setTokenValueAndTime(refreshToken, member.getEmail());

        return JwtDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .username(member.getUsername())
                .userId(member.getUserId())
                .build();
    }

    public String reissue(String refreshToken){
        String redisValue = jwtProvider.getValueFromToken(refreshToken);
        String username = jwtProvider.getUsername(refreshToken);
        String email = jwtProvider.getEmail(refreshToken);

        Member member = (Member)jwtProvider.getUserDetails(username);

        String accessToken = null;
        if(redisValue.equals(email)){
            accessToken = jwtProvider.createToken(member, "ATK", atkDuration);
        }
        log.info("===={} 토큰 재발급====", username);
        return accessToken;
    }

    public void toBlackListed(JwtDto jwtDto){
        Long duration = jwtProvider.getExpireTime(jwtDto.getAccessToken()) - new Date(System.currentTimeMillis()).getTime();
        redisService.setStringValueExpire(jwtDto.getAccessToken(), "logout", Duration.ofMillis(duration));
        redisService.deleteKey(jwtDto.getRefreshToken());
    }
}
