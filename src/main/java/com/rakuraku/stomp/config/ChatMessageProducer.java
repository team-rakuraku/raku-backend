//package com.rakuraku.stomp.config;
//
//import com.rakuraku.stomp.dto.RequestChatMessage;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ChatMessageProducer {
//    private final RabbitTemplate rabbitTemplate;
//    private final String ChAT_EXCHANGE_NAME;
//
//    public ChatMessageProducer(RabbitTemplate rabbitTemplate,
//                               @Value("${rabbitmq.chat-exchange.name}") String CHAT_EXCHANGE_NAME){
//        this.ChAT_EXCHANGE_NAME = CHAT_EXCHANGE_NAME;
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    public void sendMessage(RequestChatMessage message) {
//        rabbitTemplate.convertAndSend(ChAT_EXCHANGE_NAME, "chat.room." + message.getRoomId(), message);
//    }
//}
