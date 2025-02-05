package com.rakuraku.chatrooms.dto;

import com.rakuraku.apps.entity.Apps;
import com.rakuraku.chatrooms.entity.ChatRooms;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateRoomRequest {
    private String userId;
    private String name;

    @Builder
    public CreateRoomRequest(String userId,String name){
        this.userId = userId;
        this.name = name;
    }
}
