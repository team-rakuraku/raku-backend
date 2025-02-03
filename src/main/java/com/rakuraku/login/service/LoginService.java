package com.rakuraku.login.service;

import com.rakuraku.apps.entity.Apps;
import com.rakuraku.apps.repository.AppsRepository;
import com.rakuraku.login.dto.LoginDto;
import com.rakuraku.login.entity.Users;
import com.rakuraku.login.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


// 로그인과 관련된 코드들을 정리하는 서비스단
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final UsersService usersService;
    private final AppsRepository appsRepository;
    private final UsersRepository usersRepository;

    public boolean register(LoginDto requestDto, String appId) throws Exception{
        // Header에서 가져온 AppId
        // appId로 Apps 객체를 가져옴
        System.out.println(appId);

        Apps app = appsRepository.findByAppId(appId)
                .orElseThrow(() -> new RuntimeException("App not found"));

        if(usersService.isUuidExist(requestDto.getUserId())){
                throw new Exception("Already Exist");
        }try {
            Users users = Users.builder()
                    .appId(app)
                    .profileImage(requestDto.getProfileImageUrl())
                    .nickname(requestDto.getNickname())
                    .createdAt(LocalDateTime.now())
                    .userId(requestDto.getUserId())
                    .build();
            usersRepository.save(users);
        }catch (Exception e){
            throw new Exception("Invalid request");
        }
        return true;
    }
}
