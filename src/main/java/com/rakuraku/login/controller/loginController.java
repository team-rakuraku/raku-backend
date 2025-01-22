package com.rakuraku.login.controller;
import com.rakuraku.apps.repository.AppsRepository;
import com.rakuraku.auth.JwtProvider;
import com.rakuraku.global.ResponseDto;
import com.rakuraku.login.dto.LoginDto;
import com.rakuraku.login.dto.LoginResponseDto;
import com.rakuraku.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class loginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Object> UserLogin(@RequestHeader("Authorization") String token, @RequestHeader("App-Id") String appId, @RequestBody LoginDto loginDto) {
        boolean isSameCheck;
        try {
            Date expiredDate = loginService.GetExpiredDay(token); // 나오게 하고 싶음 payload에 exp 지정해야한다.
            isSameCheck = loginService.isSameSubandUuid(token, loginDto);
            if (isSameCheck) {
                loginService.register(loginDto, appId);
                return ResponseEntity.ok().body(LoginResponseDto.response(HttpStatus.OK, "User information returned successfully.", loginDto.getUserId(), expiredDate));
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // 또는 다른 적절한 상태 코드
                        .body(ResponseDto.response(HttpStatus.UNAUTHORIZED, "User information does not match.", false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }

    }


}
