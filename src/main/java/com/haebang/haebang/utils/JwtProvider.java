package com.haebang.haebang.utils;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    final private Key key;

    public JwtProvider(@Value("${jwt.secret}") String secretKey){
        System.out.println("jwt 시크릿 키는 ="+secretKey);
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String username){
        Claims claims = Jwts.claims();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60L))// 한시간
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token){
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody().get("username", String.class);
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("유효하지 않은 Jwt Token");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지않는 JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("토큰이 비어있는 예외", e);
            throw new CustomException(CustomErrorCode.EMPTY_ACCESS_TOKEN);
        }
        return false;
    }
}
