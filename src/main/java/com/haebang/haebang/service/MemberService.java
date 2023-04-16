package com.haebang.haebang.service;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.repository.MemberRepository;
import com.haebang.haebang.entity.Member;
import com.haebang.haebang.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    final private JwtProvider jwtUtil;
//    final private AuthenticationManagerBuilder authenticationManagerBuilder;
    final private MemberRepository memberRepository;
    final private BCryptPasswordEncoder encoder;

//    public JwtDto login(String username, String password) {
//        return null;
//    }

    public String join(String username, String password){
        if( memberRepository.findByUsername(username).isPresent() ) {
            throw new CustomException(CustomErrorCode.ALREADY_JOINED_MEMBER);
        }
        memberRepository.save(Member.builder()
                .username(username)
                .password(encoder.encode( password )).build());
        return "회원가입 성공";
    }

    public String login(String username, String password){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new CustomException(CustomErrorCode.INVALID_MEMBER_INFO) );// 존재하지 않는 사용자
        if( !encoder.matches(  password, member.getPassword()) ){
            throw new CustomException(CustomErrorCode.INVALID_MEMBER_INFO);
        }
        String token = jwtUtil.createToken(member.getUsername());
        return token;
    }
}
