package com.rakuraku.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // access token 검증
    public boolean validateAccessToken(String accessToken) {
        return Optional.ofNullable(accessToken)
                .filter(token -> !token.trim().isEmpty())
                .map(this::parseClaims)
                .map(this::isTokenNotExpired)
                .orElse(false);
    }

    private Jws<Claims> parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isTokenNotExpired(Jws<Claims> claims) {
        return claims != null && !claims.getBody().getExpiration().before(new Date());
    }

    public Date GetExpiredDay(String accessToken) throws IllegalArgumentException {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
        Date expiration = claims.getBody().getExpiration();
        return expiration;
    }


    // JWT에서 sub 클레임을 추출하는 메서드
    public String getSubjectFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // sub 클레임 반환
    }


    // Authorization Header를 통한 인증
    // Client가 Server에게 보내는 HttpServletRequest에서 Token값을 추출하는 과정
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
