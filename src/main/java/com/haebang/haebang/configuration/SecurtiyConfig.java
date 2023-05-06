package com.haebang.haebang.configuration;

import com.haebang.haebang.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurtiyConfig {
    final private JwtProvider jwtProvider;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic().disable()
                .csrf().disable() // cross site 도메인 다를때 허용
                .cors() //cross site
                .and()
                .headers().frameOptions().disable()// h2 사용 iframe 허용
                .and()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**", "/templates/**", "/").permitAll()
                .antMatchers("/map*", "/hello").permitAll()
                .antMatchers("/api/member/test","/api/apt/item", "/api/apt/item/**").authenticated()
                .antMatchers("/api/member/*").anonymous()
                .and()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
                // usernamepasswordAuthen 필터를 거치기 전에 jwt필터를 거치도록 설정
        return http.build();
        // 로그인 실패시 엔드포인트 or exception ?
    }
}
