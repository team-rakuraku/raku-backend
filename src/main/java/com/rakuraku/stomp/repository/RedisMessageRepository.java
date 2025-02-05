package com.rakuraku.stomp.repository;

import com.rakuraku.stomp.dto.RedisChatMessage;
import com.rakuraku.stomp.dto.RequestChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
@Slf4j
public class RedisMessageRepository {
    // topic에 발행되는 메세지를 처리할 리스너
//    private final RedisMessageListenerContainer redisMessageListenerContainer;
    // 구독 처리 서비스
//    private final RedisSubscriber redisSubscriber;

    private final RedisTemplate<String, Object> redisTemplate;
    // 채팅방의 대화 메시지를 발행하기 위한 레디스 토픽 정보.
    private final Map<String, ChannelTopic> topics = new HashMap<>();

//    public void enterChatRoom(String roomId){
//        ChannelTopic topic = topics.get(roomId);
//        if (topic == null){
//            topic = new ChannelTopic(roomId);
//            redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
//            topics.put(roomId, topic);
//
//            log.info("Created and stored new topic for roomId = {}", roomId);
//        }else {
//            log.info("토픽 중복 생성");
//        }
//    }
//
//    public void deleteChatRoom(String roomId){
//        redisMessageListenerContainer.removeMessageListener(redisSubscriber, topics.get(roomId));
//        topics.remove(roomId);
//    }

    // 대화 내용을 Redis에 저장
    public void saveChatMessage(String roomId, RedisChatMessage chatMessage) {
        redisTemplate.opsForList().rightPush(roomId, chatMessage); // 리스트에 추가
    }

    public List<Object> getChatHistory(String roomId) {
        return redisTemplate.opsForList().range(roomId, 0, -1); // 전체 대화 내역을 가져옴
    }
}
