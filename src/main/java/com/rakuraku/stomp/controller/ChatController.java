package com.rakuraku.stomp.controller;

import com.rakuraku.stomp.dto.MongoMessage;
import com.rakuraku.stomp.dto.RequestChatMessage;
import com.rakuraku.stomp.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final ChatRepository chatRepository;
    @MessageMapping("/{roomId}")
    @SendTo("/topic/{roomId}")
    public RequestChatMessage receive(@DestinationVariable String roomId, @Payload RequestChatMessage message) {
        log.info("Received message for room {}: {}", roomId, message);
        // 타입 chat만 저장되게
        if (message.getType().equals(RequestChatMessage.Type.CHAT)) {
            MongoMessage mongoMessage = MongoMessage.builder()
                    .content(message.getContent())
                    .userId(message.getUsersId())
                    .appId(message.getAppId())
                    .time(new Date())
                    .build();
            chatRepository.save(mongoMessage);

        }
        return message;
    }
}
