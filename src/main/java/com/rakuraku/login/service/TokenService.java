package com.rakuraku.login.service;

import com.rakuraku.auth.JwtProvider;
import com.rakuraku.login.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

// JWT토큰과 관련된 코드들을 정리하는 서비스단
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;

    // 토큰 유효성 검사
    public boolean validateAccessToken(String accessToken){
        return jwtProvider.validateAccessToken(accessToken);
    }

    // sub와 uuid가 같은지 확인
    // accessToken안에 payload안에 claim안에 sub 어케 꺼내와?
    public boolean isSameSubandUuid(String accessToken, LoginDto loginDto){
        String sub = jwtProvider.getSubjectFromToken(accessToken);
        String uuid = loginDto.getUserId();
        // sub랑 uuid가 같다면
        return sub.equals(uuid);

    }

    public Date getExpiredDay(String accessToken) {
        try {
            return jwtProvider.GetExpiredDay(accessToken);
        } catch (Exception e) {
            log.error("Token validation error: {}", e.getMessage());
            throw e; // 예외를 다시 던짐
        }
    }

    public String getSubjectFromToken(String token){
        return jwtProvider.getSubjectFromToken(token);
    }
    // validate token + isSameSubandUuid
    public boolean isValidatedAndSameSubAndUuid(String accessToken, String uuid){
        if (validateAccessToken(accessToken)){
            return getSubjectFromToken(accessToken.trim()).equals(uuid.trim());
        }
        return false;
    }

}
