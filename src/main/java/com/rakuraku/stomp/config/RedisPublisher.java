package com.rakuraku.stomp.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakuraku.stomp.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void publish(ChatMessage message) {
        try {
            redisTemplate.convertAndSend("chat", message); // "chat" 채널에 메시지 발행
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish message", e);
        }
    }
}
