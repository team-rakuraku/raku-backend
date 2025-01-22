package com.rakuraku.login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class LoginDto {
    private String userId;
    private String nickname;
    private String profileImageUrl;

    public LoginDto(String userId, String nickname, String profileImageUrl){
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.userId = userId;
    }
}
