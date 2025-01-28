package com.rakuraku.chatrooms.controller;

import com.rakuraku.chatrooms.dto.CreateRoomRequest;
import com.rakuraku.chatrooms.dto.GetUserChatRoomsResponseDto;
import com.rakuraku.chatrooms.dto.GetUserIdRequestDto;
import com.rakuraku.chatrooms.entity.ChatRooms;
import com.rakuraku.chatrooms.service.ChatRoomsService;
import com.rakuraku.global.ResponseDto;
import com.rakuraku.login.entity.Users;
import com.rakuraku.login.service.UsersService;
import com.rakuraku.user_chat_rooms.service.UserChatRoomsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/chatrooms")
public class ChatRoomsController {

    private final UserChatRoomsService userChatRoomsService;
    private final ChatRoomsService chatRoomsService;
    private final UsersService usersService;

    @PostMapping()
    public ResponseEntity<Object> createChatRooms(@RequestHeader("Authorization") String token, @RequestHeader("App-Id") String appId, @RequestBody CreateRoomRequest createRoomRequest){
        try {
            boolean isCreateChatRoom = chatRoomsService.createChatRooms(token, appId, createRoomRequest);
            if (isCreateChatRoom){
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseDto.response(HttpStatus.CREATED, "방 생성 성공"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "방 생성 실패", null));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    // user에 넣을지... 어디에 넣을 지 고민이다.
    @PostMapping("/user")
    public ResponseEntity<Object> getUserChatRooms(@RequestBody GetUserIdRequestDto userIdRequestDto){
        try {
            log.info(userIdRequestDto.getUserId());
            List<ChatRooms> chatRoomsList = chatRoomsService.getUserChatRooms(userIdRequestDto.getUserId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(GetUserChatRoomsResponseDto.response(HttpStatus.OK, "유저 정보에 맞는 방 정보들 불러오기 성공", chatRoomsList));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }
}
