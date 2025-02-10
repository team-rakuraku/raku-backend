package com.rakuraku.chatrooms.repository;

import com.rakuraku.chatrooms.entity.ChatRooms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {
    Page<ChatRooms> findAllByOrderByCreatedAtDesc(Pageable pageable);
}