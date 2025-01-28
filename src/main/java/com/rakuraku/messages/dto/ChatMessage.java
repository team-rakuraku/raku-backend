package com.rakuraku.messages.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ChatMessage {
    private String sender;
    private String content;

    @Builder
    public ChatMessage(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }
}
