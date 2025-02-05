package com.rakuraku.stomp.controller;

import com.rakuraku.stomp.config.RedisPublisher;
import com.rakuraku.stomp.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
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
    private final RedisPublisher redisPublisher;
    private final RedisMessageListenerContainer redisMessageListenerContainer;
//    private final ChatRoomRepository chatRoomService;

    @MessageMapping("/{roomId}") // 클라이언트가 방 ID에 대한 메시지를 보낼 때 호출됨
    public void receive(@DestinationVariable String roomId, @Payload ChatMessage message) {
        log.info("Received message for room {}: {}", roomId, message);
        message.setRoomId(roomId);
        redisPublisher.publish(message);
    }

//    @MessageMapping("/enter/{roomId}") // 클라이언트가 방 ID에 들어올 때 호출됨
//    public void enterChatRoom(@DestinationVariable String roomId) {
//        chatRoomService.enterChatRoom(roomId);
//    }
//
//    @MessageMapping("/leave/{roomId}") // 클라이언트가 방 ID에서 나갈 때 호출됨
//    public void leaveChatRoom(@DestinationVariable String roomId) {
//        chatRoomService.deleteChatRoom(roomId);
//    }
}
