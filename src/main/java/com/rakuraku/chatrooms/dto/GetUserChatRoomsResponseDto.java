package com.rakuraku.chatrooms.dto;

import com.rakuraku.chatrooms.entity.ChatRooms;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class GetUserChatRoomsResponseDto {
    private HttpStatus status; // 응답 상태
    private String message; // 응답 메시지
    private List<ChatRooms> chatRoomsList; // 사용자가 속한 채팅방 리스트

    // 생성자
    public GetUserChatRoomsResponseDto(HttpStatus status, String message, List<ChatRooms> chatRoomsList) {
        this.status = status;
        this.message = message;
        this.chatRoomsList = chatRoomsList;
    }

    // 정적 메서드로 DTO 객체 생성
    public static GetUserChatRoomsResponseDto response(HttpStatus status, String message, List<ChatRooms> chatRoomsList) {
        return GetUserChatRoomsResponseDto.builder()
                .status(status)
                .message(message)
                .chatRoomsList(chatRoomsList)
                .build();
    }
}
