package com.rakuraku.stomp.dto;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String appId;
    private String usersId;
    private String content;
    private Type type;
    public enum Type {
        ENTER, CHAT, LEAVE;
    }
}
