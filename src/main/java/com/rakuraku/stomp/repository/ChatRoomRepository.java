//package com.rakuraku.stomp.repository;
//
//import com.rakuraku.stomp.config.RedisSubscriber;
//import com.rakuraku.stomp.dto.ChatRoom;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Repository;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Map;
//
//@RequiredArgsConstructor
//@Repository
//@Slf4j
//public class ChatRoomRepository {
//    // topic에 발행되는 메세지를 처리할 리스너
//    private final RedisMessageListenerContainer redisMessageListenerContainer;
//    // 구독 처리 서비스
//    private final RedisSubscriber redisSubscriber;
//
//    private final RedisTemplate<String, Object> redisTemplate;
//    // 채팅방의 대화 메시지를 발행하기 위한 레디스 토픽 정보.
//    private final Map<String, ChannelTopic> topics = new HashMap<>();
//
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
//}
