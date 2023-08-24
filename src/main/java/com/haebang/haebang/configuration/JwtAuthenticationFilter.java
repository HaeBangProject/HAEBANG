package com.haebang.haebang.configuration;

import com.haebang.haebang.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // authorization 비었으면 로그인 안함
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        String token = resolve(authorization);

            if (token != null && token.length() > 0 && jwtProvider.validateToken(token)) {
                // 유효한 토큰 일때

                String type = jwtProvider.getTokenType(token);
                if (type.equals("ATK")) {// 엑세스 토큰 일떄
                    log.info("유효한 엑세스 토큰요청");

                    if ( !jwtProvider.getValueFromToken(token).equals("logout") ) {
                        // 로그아웃되지 않은 ATK라면 정상 작동 하도록
                        String username = jwtProvider.getUsername(token);
                        UserDetails userDetails = jwtProvider.getUserDetails(username);

                        // 사용자 정보를 넣은 authentication 인증객체를 만들어 넣어주면 -> authentication에서 꺼내 쓸수 있음
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(username, null,
                                        userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }else{log.warn("로그아웃된 ATK");}
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
