package com.haebang.haebang.configuration;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.repository.MemberRepository;
import com.haebang.haebang.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // authorization 비었으면 로그인 안함
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization={}", authorization);

        String token = resolve(authorization);
        log.info("token={}", token);

            if (token != null && token.length() > 0 && jwtProvider.validateToken(token)) {
                // 유효한 토큰 일때
                log.info("유효한 토큰임 필터 검사1");

                String type = jwtProvider.getTokenType(token);
                if (type.equals("ATK")) {// 엑세스 토큰 일떄
                    log.info("엑세스 토큰일때");

                    if (jwtProvider.getValueFromToken(token) == null) {
                        log.info("로그아웃된 토큰인지 살펴봄" + jwtProvider.getValueFromToken(token));
                        // 로그아웃되지 않은 ATK라면 정상 작동 하도록
                        String username = jwtProvider.getUsername(token);
                        UserDetails userDetails = jwtProvider.getUserDetails(username);
                        // 사용자 정보를 넣은 authentication 인증객체를 만들어 넣어주면 -> authentication에서 꺼내 쓸수 있음
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(username, null,
                                        userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        log.info(authorization + "통과");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

            }
        filterChain.doFilter(request, response);
    }



    String resolve(String authorization){
        if(authorization == null|| !authorization.startsWith("Bearer ")){
            return null;
        }
        return authorization.split(" ")[1];
    }

}
