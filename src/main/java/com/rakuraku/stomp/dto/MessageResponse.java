package com.rakuraku.stomp.dto;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private String roomId;
    private String userId;
    private String content;
    private String time;
    private Type type;
    public enum Type {
        ENTER, CHAT, LEAVE;
    }
}
