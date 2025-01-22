package com.rakuraku.login.service;

import com.rakuraku.apps.entity.Apps;
import com.rakuraku.apps.repository.AppsRepository;
import com.rakuraku.apps.service.AppsProvider;
import com.rakuraku.auth.JwtProvider;
import com.rakuraku.login.dto.LoginDto;
import com.rakuraku.login.entity.Users;
import com.rakuraku.login.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtProvider jwtProvider;
    private final UsersService usersService;
    private final AppsRepository appsRepository;
    private final UsersRepository usersRepository;

    // sub와 uuid가 같은지 확인
    // accessToken안에 payload안에 claim안에 sub 어케 꺼내와?
    public boolean isSameSubandUuid(String accessToken, LoginDto loginDto){
        String sub = jwtProvider.getSubjectFromToken(accessToken);
        String uuid = loginDto.getUserId();
        // sub랑 uuid가 같다면
        return sub.equals(uuid);

    }

    public Date GetExpiredDay(String accessToken) {
        try {
            return jwtProvider.GetExpiredDay(accessToken);
        } catch (Exception e) {
            log.error("Token validation error: {}", e.getMessage());
            throw e; // 예외를 다시 던짐
        }
    }


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
                    .userId(requestDto.getUserId())
                    .build();
            usersRepository.save(users);
        }catch (Exception e){
            throw new Exception("Invalid request");
        }
        return true;
    }
}
