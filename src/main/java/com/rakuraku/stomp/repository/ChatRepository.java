package com.rakuraku.stomp.repository;

import com.rakuraku.stomp.dto.MongoMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<MongoMessage, String> {
    List<MongoMessage> findMongoMessageByRoomId(String roomId, Pageable pageable);
}
