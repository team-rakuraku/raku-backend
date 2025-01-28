package com.rakuraku.login.controller;

import com.rakuraku.login.dto.GetUserListResponseDto;
import com.rakuraku.login.entity.Users;
import com.rakuraku.login.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    @GetMapping
    public ResponseEntity<GetUserListResponseDto> getUsers(){
        List<Users> usersList = usersService.getAllUsers();
       return ResponseEntity.status(HttpStatus.OK)
               .body(GetUserListResponseDto.response(HttpStatus.OK, "유저 정보 불러오기 성공", usersList));
    }
}
