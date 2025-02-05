package com.rakuraku.stomp.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestChatMessage {
    private String appId;
    private String usersId;
    private String content;
    private String roomId;
    private Type type;
    public enum Type {
        ENTER, CHAT, LEAVE;
    }
}
