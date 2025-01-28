package com.rakuraku.user_chat_rooms.service;

import com.rakuraku.chatrooms.entity.ChatRooms;
import com.rakuraku.login.entity.Users;
import com.rakuraku.user_chat_rooms.entity.UserChatRooms;
import com.rakuraku.user_chat_rooms.repository.UserChatRoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserChatRoomsService {
    private  final UserChatRoomsRepository userChatRoomsRepository;

    public List<ChatRooms> getUserChatRooms(Users users){
        List<UserChatRooms> userChatRooms = userChatRoomsRepository.findByUser(users);
        return userChatRooms.stream()
                .map(UserChatRooms::getChatRoom)
                .collect(Collectors.toList());
    }


}
