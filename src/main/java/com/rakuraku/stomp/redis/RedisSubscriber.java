package com.rakuraku.stomp.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakuraku.stomp.dto.RedisChatMessage;
import com.rakuraku.stomp.dto.RequestChatMessage;
import com.rakuraku.stomp.repository.RedisMessageRepository;
import com.rakuraku.stomp.service.RedisMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisMessageService redisMessageService;

    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            log.info("Received JSON: {}", message);
            RedisChatMessage chatMessage = objectMapper.readValue(publishMessage, RedisChatMessage.class);
            log.info("Received Redis message: {}", chatMessage);
            redisMessageService.saveMessageInRedis(chatMessage.getRoomId(),chatMessage);
            // STOMP를 통해 구독 중인 클라이언트에게 전달
            messagingTemplate.convertAndSend("/topic/" + chatMessage.getRoomId(), chatMessage);
        } catch (Exception e) {
            log.error("Failed to process message", e);
        }
    }
}
