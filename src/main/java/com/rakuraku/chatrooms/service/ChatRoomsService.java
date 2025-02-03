package com.rakuraku.chatrooms.service;

import com.rakuraku.apps.entity.Apps;
import com.rakuraku.apps.repository.AppsRepository;
import com.rakuraku.chatrooms.dto.CreateRoomRequest;
import com.rakuraku.chatrooms.entity.ChatRooms;
import com.rakuraku.chatrooms.repository.ChatRoomsRepository;
import com.rakuraku.login.entity.Users;
import com.rakuraku.login.repository.UsersRepository;
import com.rakuraku.login.service.TokenService;
import com.rakuraku.login.service.UsersService;
import com.rakuraku.user_chat_rooms.entity.UserChatRooms;
import com.rakuraku.user_chat_rooms.repository.UserChatRoomsRepository;
import com.rakuraku.user_chat_rooms.service.UserChatRoomsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomsService {
    private final ChatRoomsRepository chatRoomsRepository;
    private final UserChatRoomsRepository userChatRoomsRepository;
    private final TokenService tokenService;
    private final UsersRepository usersRepository;
    private final AppsRepository appsRepository;
    private final UsersService usersService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserChatRoomsService userChatRoomsService;

    @Transactional
    // 방 생성
    public boolean createChatRooms(String token, String appId, CreateRoomRequest createRoomRequest) {
        // JWT 토큰 검증
        if (!tokenService.isValidatedAndSameSubAndUuid(token, createRoomRequest.getUserId())) {
            return false;
        }

        // 채팅방 생성
        ChatRooms chatRoom = createChatRoom(appId, createRoomRequest);

        // 사용자를 채팅방에 추가
        addUserToChatRoom(createRoomRequest.getUserId(), chatRoom, UserChatRooms.Role.ADMIN);

        // 초대된 사용자 추가 및 메시지 전송
//        for (String invitedUserId : createRoomRequest.getInvitedUserIds()) {
//            addUserToChatRoom(invitedUserId, chatRoom, UserChatRooms.Role.MEMBER);
//            sendChatRoomToUser(chatRoom, invitedUserId);
//        }
        // 소켓으로 생성됐다고 알려주기
        sendChatRoomToUser(chatRoom);

        return true;
    }
    @Transactional
    public ChatRooms createChatRoom(String appId, CreateRoomRequest createRoomRequest) {
        Apps apps = appsRepository.findByAppId(appId)
                .orElseThrow(() -> new RuntimeException("Apps not found"));

        ChatRooms chatRoom = ChatRooms.builder()
                .name(createRoomRequest.getName())
                .appId(apps)
                .count(1)
                .type(createRoomRequest.getType())
                .createdAt(LocalDateTime.now())
                .build();

        return chatRoomsRepository.save(chatRoom);
    }
    @Transactional
    public void addUserToChatRoom(String userId, ChatRooms chatRoom, UserChatRooms.Role role) {
        Users user = usersRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserChatRooms userChatRoom = UserChatRooms.builder()
                .user(user)
                .chatRoom(chatRoom)
                .joinedAt(LocalDateTime.now())
                .role(role)
                .build();

        userChatRoomsRepository.save(userChatRoom);
    }

    private void sendChatRoomToUser(ChatRooms chatRoom) {
        log.info("Sending chat room: {}", chatRoom.getName());
        messagingTemplate.convertAndSend("/topic/chatroom/", chatRoom);
    }
    // 유저 리스트
    @Transactional
    public List<ChatRooms> getUserChatRooms(String userId){
        Users users = usersRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User account not found"));
        List<ChatRooms> chatRoomsList = userChatRoomsService.getUserChatRooms(users);
        return chatRoomsList;
    }

}
