package com.rakuraku.chatrooms.dto;

import lombok.Data;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드를 포함하는 생성자 추가
public class GetUserIdRequestDto {
    private String userId; // 사용자 ID
}
