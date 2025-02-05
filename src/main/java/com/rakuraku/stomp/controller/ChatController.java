package com.rakuraku.stomp.controller;

import com.rakuraku.stomp.dto.RequestChatMessage;
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

    @MessageMapping("/{roomId}")
    @SendTo("/topic/{roomId}")
    public RequestChatMessage receive(@DestinationVariable String roomId, @Payload RequestChatMessage message) {
        log.info("Received message for room {}: {}", roomId, message);
        return message;
    }
}
