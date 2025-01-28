package com.rakuraku.stomp.config;

import com.rakuraku.auth.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;


// 스프링 버전 변경에 따라 stompHandler 셀프로 만들어 줘야 한다.
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {
    private final JwtProvider jwtProvider;
    private static final Logger log = LoggerFactory.getLogger( StompHandler.class );
    // 메시지 전송 전에 실행되는 로직
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // nativeHeaders에서 Authorization과 userId 가져오기
//        Map<String, List<String>> nativeHeaders = (Map<String, List<String>>) message.getHeaders().get("nativeHeaders");
//        String authorization = nativeHeaders != null && nativeHeaders.containsKey("Authorization")
//                ? nativeHeaders.get("Authorization").get(0) // Authorization 첫 번째 값
//                : null;
//
//        String userId = nativeHeaders != null && nativeHeaders.containsKey("userId")
//                ? nativeHeaders.get("userId").get(0) // userId 첫 번째 값
//                : null;
//
//        // 로그 출력
//        log.info("Authorization: {}", authorization);
//        log.info("User ID: {}", userId);
//        log.info("Stomp Handler 실행");

        return message;
    }

    // 메시지 전송 후에 실행되는 로직
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        switch ((Objects.requireNonNull(accessor.getCommand()))) {
            case CONNECT:
                // 유저가 웹소켓 연결(connect)하면 호출됨
                log.info("웹소켓 연결 => {}", sessionId);
                break;
            case DISCONNECT:
                // 유저가 웹소켓 연결 끊으면(disconnect) 호출됨 or 세션이 끊어졌을 때 호출
                log.info("웹소켓 끊음=> {}", sessionId);
                break;
            default:
                break;
        }

    }

    // 메시지 전송 완료 후에 실행되는 로직
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
    }

    // 메시지 수신 전에 실행되는 로직
    @Override
    public boolean preReceive(MessageChannel channel) {
        return true;
    }

    // 메시지 수신 후에 실행되는 로직
    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return message;
    }

    // 메시지 수신 완료 후에 실행되는 로직
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
    }
}
