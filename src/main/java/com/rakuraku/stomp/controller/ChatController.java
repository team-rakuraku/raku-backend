package com.rakuraku.stomp.controller;

import com.rakuraku.stomp.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class ChatController {
    @MessageMapping("/{roomId}") // 클라이언트가 방 ID에 대한 메시지를 보낼 때 호출됨
    @SendTo("/topic/{roomId}") // 해당 방에 구독한 모든 클라이언트에게 메시지를 전송
    public ChatMessage receive(@DestinationVariable String roomId, @Payload ChatMessage message) {
        log.info("Received message for room {}: {}", roomId, message);
        return message;
    }
}
