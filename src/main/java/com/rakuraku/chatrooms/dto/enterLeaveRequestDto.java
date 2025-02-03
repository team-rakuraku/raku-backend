package com.rakuraku.chatrooms.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class enterLeaveRequestDto {
    private String userId;

    @Builder
    public enterLeaveRequestDto(String userId){
        this.userId = userId;
    }
}
