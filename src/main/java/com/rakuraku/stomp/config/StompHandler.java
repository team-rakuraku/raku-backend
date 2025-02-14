package com.rakuraku.stomp.config;

import com.rakuraku.login.auth.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

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
        return message;
    }

    // 메시지 전송 후에 실행되는 로직
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        // 미췬 .. 이거 땜에 rabbitMQ heartbeat하는데 에러뜬 ㅅㅂ
        // 사유는 [headers={simpMessageType=HEARTBEAT, simpSessionAttributes={}, simpHeartbeat=[J@1719acc8, simpSessionId=fqlfbmhn}] => 하트비트의 헤더
        // [headers={simpMessageType=CONNECT, stompCommand=CONNECT, nativeHeaders={accept-version=[1.1,1.0], heart-beat=[10000,10000]}, simpSessionAttributes={}, simpHeartbeat=[J@3acc16a3, simpSessionId=fqlfbmhn}] => 커넥트의 헤더
        // ㅋㅋ stompCommand가 없으니까 당연히 널이 뜨지
//        switch (accessor.getCommand()) {
//            case CONNECT:
//                // 유저가 웹소켓 연결(connect)하면 호출됨
//                log.info("웹소켓 연결 => {}", sessionId);
//                break;
//            case DISCONNECT:
//                // 유저가 웹소켓 연결 끊으면(disconnect) 호출됨 or 세션이 끊어졌을 때 호출
//                log.info("웹소켓 끊음=> {}", sessionId);
//                break;
//            default:
//                break;
//        }

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