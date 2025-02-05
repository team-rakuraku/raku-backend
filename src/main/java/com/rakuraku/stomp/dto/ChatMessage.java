package com.rakuraku.stomp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String appId;
    private String usersId;
    private String content;
    private String roomId;
    private Type type;
    public enum Type {
        ENTER, CHAT, LEAVE;
    }
}
