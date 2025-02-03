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
    private Apps appId;
    private ChatRooms.Type type;
    private LocalDateTime createdAt;
//    private List<String> invitedUserIds; // 초대할 사용자 ID 목록 추가

    @Builder
    public CreateRoomRequest(String userId,String name, Apps appId, String type, LocalDateTime createdAt){
        this.userId = userId;
        this.name = name;
        this.appId = appId;
        this.type = ChatRooms.Type.valueOf(type);
        this.createdAt = createdAt;
//        this.invitedUserIds = invitedUserIds; // 초대할 사용자 ID 목록
    }
}
