package com.rakuraku.stomp.service;

import com.rakuraku.stomp.dto.MongoMessage;
import com.rakuraku.stomp.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MongoTemplate mongoTemplate;
    private final ChatRepository chatRepository;

    public List<MongoMessage> getMongoMessageByRoomId(String roomId, Pageable pageable){
        return chatRepository.findMongoMessageByRoomId(roomId, pageable);
    }
}
