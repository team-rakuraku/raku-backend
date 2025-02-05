package com.rakuraku.stomp.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedisChatMessage {
    private String appId;
    private String usersId;
    private String content;
    private String roomId;
    private LocalDateTime createdAt;
}
