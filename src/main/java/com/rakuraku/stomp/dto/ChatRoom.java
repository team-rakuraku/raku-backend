package com.rakuraku.stomp.dto;

import lombok.Data;

@Data
public class ChatRoom {
    private String roomId;
    private String appId;
    private String name;

    public static ChatRoom create(String roomId, String appId,String name){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = roomId;
        chatRoom.appId = appId;
        chatRoom.name = name;
        return chatRoom;
    }
}
