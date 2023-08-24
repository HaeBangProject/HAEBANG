package com.haebang.haebang.service;

import com.haebang.haebang.entity.Member;
import com.haebang.haebang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member entity = memberRepository.findByUsername(username).get();
        if(entity==null){
            throw new UsernameNotFoundException(username+"을 찾을 수 없습니다");
            // 로그인 실패시 예외 던지기
        }
        return Member.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(bCryptPasswordEncoder.encode( entity.getPassword()) )
                .role("ROLE_USER")
                .build();
    }
}
