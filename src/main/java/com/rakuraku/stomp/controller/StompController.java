package com.rakuraku.stomp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Controller
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
public class StompController {
    private static final Logger LOGGER = LoggerFactory.getLogger( StompController.class );
    private final SimpMessageSendingOperations sendingOperations;
    // 새로운 사용자가 웹 소켓 연결할 때 실행
    // 한 개의 매개변수만 가질 수 있음
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event){
        LOGGER.info("Received a new web socket connection");
    }

    // 사용자가 웹 소켓 연결 끊으면 실행
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        LOGGER.info("sessionId Disconnected : " + sessionId);
    }
}
