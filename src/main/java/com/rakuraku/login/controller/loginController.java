package com.rakuraku.login.controller;
import com.rakuraku.apps.repository.AppsRepository;
import com.rakuraku.auth.JwtProvider;
import com.rakuraku.global.ResponseDto;
import com.rakuraku.login.dto.LoginDto;
import com.rakuraku.login.dto.LoginResponseDto;
import com.rakuraku.login.service.LoginService;
import com.rakuraku.login.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class loginController {

    private final LoginService loginService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> UserLogin(@RequestHeader("Authorization") String token, @RequestHeader("App-Id") String appId, @RequestBody LoginDto loginDto) {
        try{
            if (tokenService.validateAccessToken(token)){ // 토큰이 유효하고
                if (tokenService.isSameSubandUuid(token, loginDto)){ // uuid와 sub가 일치하면
                    loginService.register(loginDto, appId); // 등록해라
                    return ResponseEntity.ok().body(LoginResponseDto.response(HttpStatus.OK, "success", loginDto.getUserId(), tokenService.getExpiredDay(token)));
                }else{ // uuid와 sub이 불일치하면
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseDto.response(HttpStatus.UNAUTHORIZED, "uuid와 sub가 일치하지 않습니다.", false));
                }
            } else { // 토큰이 유효하지 않다면
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseDto.response(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.", false));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }

    }


}
