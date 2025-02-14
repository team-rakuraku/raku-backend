package com.rakuraku.stomp.config;

import com.rakuraku.login.auth.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    private final StompHandler stompHandler;
    private final JwtProvider jwtProvider;

    /**
     * 웹소켓 핸드쉐이크 커넥션을 생성할 경로
     * setAllowedOrignPatterns("*") CORS 설정 모두 허용
     * withSockJS을 통해 웹소켓을 지원하지 않는 브라우저는 sockJS를 사용하도록 -> 테스트 할 때만 주석
     * **/
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
    /**
     * configureMessageBroker 메세지 브로커 설정을 위한 메소드
     * registry.enableSimpleBroker("/queue", "/topic") 스프링에서 제공하는 내장 브로커를 사용한다.
     * queue는 메세지가 1:1로 송신될 때
     * topic은 메세지가 1:N 여러멍에서 송신될 때
     * registry.setApplicationDestinationPrefixes("/app")
     * 상황에 따라 브로커로 바로 가는 것이 아니라 핸들러를 통해 메세지에 처리나 가공을 할 수 있도록 할 수 있다.
     * /app이 붙어있는 경로로 발신되면 해당 경로를 처리하고 있는 핸들러로 전달된다.
     * **/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        // 메세지를 구독하는 요청 url => 메세지를 받을 때
//        registry.enableSimpleBroker("/queue", "/topic");
//        // 메세지를 발행하는 요청 url => 메세지 보낼 때
//        registry.setApplicationDestinationPrefixes("/app");
        // 메시지 브로커 설정
        registry.setPathMatcher(new AntPathMatcher(".")); // url을 chat/room/3 -> chat.room.3으로 참조하기 위한 설정

        registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue")
                .setRelayHost("localhost")
                .setVirtualHost("/")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
//                .setSystemHeartbeatReceiveInterval(10000)
//                .setSystemHeartbeatSendInterval(10000)
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }


}
