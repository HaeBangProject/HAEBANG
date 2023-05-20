package com.haebang.haebang.utils;

import com.haebang.haebang.constant.CustomErrorCode;
import com.haebang.haebang.dto.JwtDto;
import com.haebang.haebang.dto.MemberReqDto;
import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.repository.MemberRepository;
import com.haebang.haebang.service.CustomRedisService;
import com.haebang.haebang.service.MemberService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    final private Key key;
    final private CustomRedisService redisService;
    final private MemberRepository memberRepository;

    public JwtProvider(@Value("${jwt.secret}") String secretKey,
                       CustomRedisService redisService, MemberRepository memberRepository){
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
        this.redisService = redisService;
        this.memberRepository = memberRepository;
    }

    /**
     * @param dto 로그인 한 사람의 정보
     * @param typ ATK, RFT 타입
     * @param duration 1시간 단위
     * @return 토큰
     */
    public String createToken(MemberReqDto dto, String typ, Long duration){
        Claims claims = Jwts.claims();
        claims.put("username", dto.getUsername());
        claims.put("auth", "ROLE_USER");
        claims.put("email", dto.getEmail());

        String token = Jwts.builder()
                .setClaims(claims)
                .setHeaderParam("typ", typ)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration * 1000*60*60L))// 한시간
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public String getUsername(String token){
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody().get("username", String.class);
    }
    public String getEmail(String token){
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody().get("email", String.class);
    }

    public UserDetails getUserDetails(String username){
        return memberRepository.findByUsername(username).get();
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
        }
        return false;
    }

    public Long getExpireTime(String token){
        Date expire = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
                .getBody().getExpiration();
        return expire.getTime();
    }
    public String getTokenType(String token){
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getHeader().getType();
    }

    public String getValueFromToken(String token){
        String value = redisService.getStringValue(token);
        if(value!=null) return redisService.getStringValue(token);// logout(ATK) or email(RTK)

        new CustomException(CustomErrorCode.LOGOUTED_MEMBER_WARN);
        return null;
    }

    /**
     * @param token ATK, RTK
     * @param value logout, email
     */
    public void setTokenValueAndTime(String token, String value){
        Long duration = getExpireTime(token) - new Date(System.currentTimeMillis()).getTime();
        redisService.setStringKey(token, value, Duration.ofMillis(duration));
    }

}
