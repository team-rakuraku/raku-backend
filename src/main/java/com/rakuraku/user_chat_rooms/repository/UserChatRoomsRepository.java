package com.rakuraku.user_chat_rooms.repository;

import com.rakuraku.chatrooms.entity.ChatRooms;
import com.rakuraku.login.entity.Users;
import com.rakuraku.user_chat_rooms.entity.UserChatRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChatRoomsRepository extends JpaRepository<UserChatRooms, Long> {
    List<UserChatRooms> findByUser(Users users); // 특정 사용자의 채팅방 리스트 조회
    Optional<UserChatRooms> findByUserAndChatRoom(Users user, ChatRooms chatRoom);
    boolean existsByUserAndChatRoom(Users user, ChatRooms chatRoom);
}
