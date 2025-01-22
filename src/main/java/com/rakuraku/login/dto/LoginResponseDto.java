package com.rakuraku.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    private HttpStatus status;
    private String message;
    private String userId;
    private Date expiresAt;

    public LoginResponseDto(HttpStatus status, String message, Date expiresAt, String userId) {
        this.status = status;
        this.message = message;
        this.expiresAt = expiresAt;
        this.userId = userId;
    }

    public static LoginResponseDto response(HttpStatus status, String message, String userId, Date expiresAt) {
        return LoginResponseDto.builder()
                .status(status)
                .message(message)
                .userId(userId)
                .expiresAt(expiresAt)
                .build();
    }


    public static LoginResponseDto response(HttpStatus status, String message) {
        return response(status, message, null, null);
    }
}
