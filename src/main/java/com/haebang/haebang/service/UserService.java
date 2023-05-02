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
    final private MemberRepository memberRepository;
    final private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member entity = memberRepository.findByUsername(username).get();
//        System.out.println("loadUserByUsername: "+entity.getUsername()+","+ entity.getEmail()+","+ entity.getPassword());
        if(entity==null){
            throw new UsernameNotFoundException(username+"을 찾을 수 없습니다");
            // 로그인 실패시 예외 던지기
        }
        log.info("load by username");
        return Member.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .password(bCryptPasswordEncoder.encode( entity.getPassword()) )
                .build();
    }
}
