package com.rakuraku.login.dto;

import com.rakuraku.login.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class GetUserListResponseDto{
    private HttpStatus status;
    private String message;
    private List<Users> usersList;

    public GetUserListResponseDto(HttpStatus status, String message, List<Users> usersList){
        this.usersList = usersList;
        this.status = status;
        this.message = message;
    }

    public static GetUserListResponseDto response(HttpStatus status, String message, List<Users> usersList) {
        return GetUserListResponseDto.builder()
                .status(status)
                .message(message)
                .usersList(usersList)
                .build();
    }
}
