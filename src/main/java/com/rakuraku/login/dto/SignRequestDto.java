package com.rakuraku.login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class SignRequestDto {
    private String userId;
    private String nickname;
    private String profileImageUrl;

    public SignRequestDto(String userId, String nickname, String profileImageUrl){
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
    }
}
