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
import com.rakuraku.stomp.repository.ChatRoomRepository;
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
            throw new RuntimeException("유효하지 않은 token 입니다.");
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

    @Transactional
    public boolean enterUser(String token, String appId, String userId, Long chatRoomId){
        // JWT 토큰 검증
        if (!tokenService.isValidatedAndSameSubAndUuid(token, userId)) {
            throw new RuntimeException("유효하지 않은 token 입니다.");
        }
        // 채팅방 찾기
        ChatRooms chatRoom = chatRoomsRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
        //appId 확인
        if (!chatRoom.getAppId().getAppId().equals(appId)){
            throw new RuntimeException("유효하지 않은 appId 입니다.");
        }
        Users user = usersRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 사용자가 이미 채팅방에 있는지 확인
        boolean userExists = userChatRoomsRepository.existsByUserAndChatRoom(user,chatRoom);
        if (userExists) {
            throw new RuntimeException("이미 채팅방에 존재합니다."); // 사용자에게 적절한 메시지 제공
        }

        // 사용자를 채팅방에 추가
        addUserToChatRoom(userId, chatRoom, UserChatRooms.Role.MEMBER);
        // 인원수 추가
        chatRoom.addPerson();
        // 데이터베이스에 변경된 채팅방 저장
        chatRoomsRepository.save(chatRoom);
        // 소켓으로 입장 메시지 전송
//        sendUserEnteredMessage(chatRoom, userId);

        return true;
    }

//    private void sendUserEnteredMessage(ChatRooms chatRoom, String userId) {
//        log.info("User {} entered chat room: {}", userId, chatRoom.getName());
//        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoom.getId(), userId + " has entered the chat room.");
//    }


    @Transactional
    public boolean leaveChatRoom(String userId, Long chatRoomId) {
        // 채팅방 찾기
        ChatRooms chatRoom = chatRoomsRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        // 사용자 찾기
        Users user = usersRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 사용자를 채팅방에서 제거
        UserChatRooms userChatRoom = userChatRoomsRepository.findByUserAndChatRoom(user, chatRoom)
                .orElseThrow(() -> new RuntimeException("User not found in chat room"));

        userChatRoomsRepository.delete(userChatRoom); // 사용자 제거

        // 인원 수 감소
        chatRoom.minusPerson(); // 인원 수 감소 메서드 호출

        // 데이터베이스에 변경된 채팅방 저장
        chatRoomsRepository.save(chatRoom);

        // 소켓으로 나간 메시지 전송
//        sendUserLeftMessage(chatRoom, userId);

        return true;
    }
//    private void sendUserLeftMessage(ChatRooms chatRoom, String userId) {
//        log.info("User {} left chat room: {}", userId, chatRoom.getName());
//        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoom.getId(), userId + " has left the chat room.");
//    }
}
