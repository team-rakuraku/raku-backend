package com.rakuraku.stomp.controller;

import com.rakuraku.chatrooms.service.ChatRoomsService;
import com.rakuraku.global.ResponseDto;
import com.rakuraku.stomp.dto.RedisChatMessage;
import com.rakuraku.stomp.redis.RedisPublisher;
import com.rakuraku.stomp.dto.RequestChatMessage;
import com.rakuraku.stomp.repository.RedisMessageRepository;
import com.rakuraku.stomp.service.RedisMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final RedisMessageService redisMessageService;

    @MessageMapping("/{roomId}") // 클라이언트가 방 ID에 대한 메시지를 보낼 때 호출됨
    public void receive(@DestinationVariable String roomId, @Payload RequestChatMessage message) {
        log.info("Received message for room {}: {}", roomId, message);
        RedisChatMessage redisChatMessage = redisMessageService.changeRequestMessageToRedisMessage(message);
        redisPublisher.publish(redisChatMessage);
    }

    @GetMapping("/chat/history/{roomId}")
    public ResponseEntity<Object> getChatHistory(@PathVariable String roomId){
        List<Object> chatList = redisMessageService.getChatHistory(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.response(HttpStatus.OK,"채팅 내용 불러오기", chatList));
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
