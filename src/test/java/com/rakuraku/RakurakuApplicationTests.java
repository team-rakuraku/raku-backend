package com.rakuraku;

import com.rakuraku.stomp.dto.MongoMessage;
import com.rakuraku.stomp.dto.RequestChatMessage;
import com.rakuraku.stomp.repository.ChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class RakurakuApplicationTests {
    @Autowired
    private ChatRepository chatRepository;
    @Test
    void contextLoads() {
    }

    @Test
    void testSaveMultipleMessages() {
        // 100개의 MongoMessage 생성 및 저장
        for (int i = 0; i < 100; i++) {
            RequestChatMessage message = new RequestChatMessage();
            message.setType(RequestChatMessage.Type.CHAT);
            message.setContent("테스트 메시지 " + (i + 1));
            message.setUsersId("userId123");
            message.setAppId("rakuraku3");
            message.setRoomId("2");

            // 타입이 CHAT인 경우에만 저장
            if (message.getType().equals(RequestChatMessage.Type.CHAT)) {
                MongoMessage mongoMessage = MongoMessage.builder()
                        .content(message.getContent())
                        .userId(message.getUsersId())
                        .appId(message.getAppId())
                        .roomId(message.getRoomId())
                        .time(new Date())
                        .build();
                chatRepository.save(mongoMessage);
            }
        }

        System.out.println("100개의 메시지가 저장되었습니다.");
    }

}
