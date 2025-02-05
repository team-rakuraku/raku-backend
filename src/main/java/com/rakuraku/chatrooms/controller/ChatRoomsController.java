package com.rakuraku.chatrooms.controller;

import com.rakuraku.chatrooms.dto.CreateRoomRequest;
import com.rakuraku.chatrooms.dto.GetUserChatRoomsResponseDto;
import com.rakuraku.chatrooms.dto.GetUserIdRequestDto;
import com.rakuraku.chatrooms.dto.enterLeaveRequestDto;
import com.rakuraku.chatrooms.entity.ChatRooms;
import com.rakuraku.chatrooms.service.ChatRoomsService;
import com.rakuraku.global.ResponseDto;
import com.rakuraku.login.service.UsersService;
import com.rakuraku.user_chat_rooms.service.UserChatRoomsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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



    // 입장
    @PostMapping("/enter/{room_id}")
    public ResponseEntity<Object> enterUserChatRooms(@RequestHeader("Authorization") String token,
                                                     @RequestHeader("App-Id") String appId,
                                                     @PathVariable("room_id") Long room_id,
                                                     @RequestBody enterLeaveRequestDto enterRequestDto){
        try {
            boolean success = chatRoomsService.enterUser(token,appId,enterRequestDto.getUserId(),room_id);
            if (success){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseDto.response(HttpStatus.OK, "user has entered the chat room successfully."));
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.response(HttpStatus.BAD_REQUEST, "채팅방 입장 실패", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }
    // 나가기
    @PostMapping("/leave/{room_id}")
    public ResponseEntity<Object> leaveChatRoom(@RequestHeader("Authorization") String token,
                                                @RequestHeader("App-Id") String appId,
                                                @PathVariable("room_id") Long room_id,
                                                @RequestBody enterLeaveRequestDto leaveRequestDto){
        try {
            boolean success = chatRoomsService.leaveChatRoom(leaveRequestDto.getUserId(),room_id);
            if (success){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseDto.response(HttpStatus.OK, String.format("%s 유저가 성공적으로 %s 방에서 나갔습니다.", leaveRequestDto.getUserId(), room_id)));
            }else{
                return ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseDto.response(HttpStatus.OK, String.format("%s 유저가 %s 방에서 나가는 거 실패.", leaveRequestDto.getUserId() ,room_id)));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }
}
