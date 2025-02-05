package com.rakuraku.stomp.service;

import com.rakuraku.stomp.dto.RedisChatMessage;
import com.rakuraku.stomp.dto.RequestChatMessage;
import com.rakuraku.stomp.repository.RedisMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisMessageService {
    private final RedisMessageRepository redisMessageRepository;
    // redis에 저장하기 위한 방식으로 변환
    public RedisChatMessage changeRequestMessageToRedisMessage(RequestChatMessage message){
        RedisChatMessage redisMessage = RedisChatMessage.builder()
                .appId(message.getAppId())
                .content(message.getContent())
                .roomId(message.getRoomId())
                .createdAt(LocalDateTime.now())
                .usersId(message.getUsersId())
                .build();

        return redisMessage;
    }

    // 레디스에 저장
    public void saveMessageInRedis(String roomId, RedisChatMessage redisChatMessage){
        redisMessageRepository.saveChatMessage(roomId,redisChatMessage);
    }

    public List<Object> getChatHistory(String roomId){
        return redisMessageRepository.getChatHistory(roomId);
    }
}
