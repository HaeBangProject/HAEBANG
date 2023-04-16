package com.haebang.haebang.configuration;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    final private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // authorization 비었으면 로그인 안함
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization={}", authorization);

        String token = resolve(authorization);

        if(token!=null && jwtProvider.validateToken(token)){
            // 유효한 토큰 일때
            log.info("유효한 토큰임");

            String username = jwtProvider.getUsername(token);
            // 사용자 정보를 넣은 authentication 인증객체를 만들어 넣어주면 -> authentication에서 꺼내 쓸수 있음
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null,
                            List.of(new SimpleGrantedAuthority("USER")));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
        return;
    }

    String resolve(String authorization){
        if(authorization==null || !authorization.startsWith("Bearer ")){
            log.error("request에서 authorization 빈 요청 통과");
            return  null;
        }
        return authorization.split(" ")[1];
    }

}
