package com.rakuraku.stomp.controller;

import com.rakuraku.stomp.dto.MongoMessage;
import com.rakuraku.stomp.dto.RequestChatMessage;
import com.rakuraku.stomp.repository.ChatRepository;
import com.rakuraku.stomp.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final ChatRepository chatRepository;
    private final ChatService chatService;
    private final RabbitTemplate template;
//    @MessageMapping("/{roomId}")
//    @SendTo("/topic/{roomId}")
//    public RequestChatMessage receive(@DestinationVariable String roomId, @Payload RequestChatMessage message) {
//        log.info("Received message for room {}: {}", roomId, message);
//        // 타입 chat만 저장되게
//        if (message.getType().equals(RequestChatMessage.Type.CHAT)) {
//            chatService.saveMessage(message);
//        }
//        return message;
//    }

    @MessageMapping("chat.queue.{roomId}")
    public void receive(@DestinationVariable String roomId, @Payload RequestChatMessage message) {
        log.info(message.toString());
        if (message.getType().equals(RequestChatMessage.Type.CHAT)) {
            template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + roomId, message);
            chatService.saveMessage(message);
        } else {
            log.error("Received null message");
        }
    }
}
