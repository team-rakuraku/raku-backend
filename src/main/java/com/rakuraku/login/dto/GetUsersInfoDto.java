package com.rakuraku.login.dto;

import com.rakuraku.apps.entity.Apps;
import com.rakuraku.login.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUsersInfoDto {
    private String userId;
    private String nickname;
    private String profileImageUrl;
    private Apps appId;
    private LocalDateTime createdAt;

    public GetUsersInfoDto(Users users){
        this.nickname = users.getNickname();
        this.profileImageUrl = users.getProfileImage();
        this.userId = users.getUserId();
        this.createdAt = users.getCreatedAt();
        this.appId = users.getAppId();
    }
}
