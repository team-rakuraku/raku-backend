package com.rakuraku.stomp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
@EnableRabbit
public class RabbitConfig {
    private final String CHAT_QUEUE_NAME;
    private final String CHAT_EXCHANGE_NAME;
    private final String CHAT_ROUTING_KEY;
    private final String RABBITMQ_HOST;

    public RabbitConfig(
            @Value("${rabbitmq.chat-queue.name}") String CHAT_QUEUE_NAME,
            @Value("${rabbitmq.chat-exchange.name}") String CHAT_EXCHANGE_NAME,
            @Value("${rabbitmq.chat-routing.key}") String CHAT_ROUTING_KEY,
            @Value("${spring.rabbitmq.host}") String RABBITMQ_HOST
    ) {
        this.CHAT_QUEUE_NAME = CHAT_QUEUE_NAME;
        this.CHAT_EXCHANGE_NAME = CHAT_EXCHANGE_NAME;
        this.CHAT_ROUTING_KEY = CHAT_ROUTING_KEY;
        this.RABBITMQ_HOST = RABBITMQ_HOST;
    }

    // "chat.queue"라는 이름의 Queue 생성
    @Bean
    public Queue chatQueue() {
        return new Queue(CHAT_QUEUE_NAME, true); // durable을 true로 제공
    }

    // 4가지 Binding 전략 중 TopicExchange 전략을 사용. "chat.exchange"를 이름으로 지정
    @Bean
    public TopicExchange chatExchange() {
        return new TopicExchange(CHAT_EXCHANGE_NAME); // 내구성 있는 교환기로 설정
    }


    // Exchange와 Queue를 연결. "chat.queue"에 "chat.exchange" 규칙을 Binding
    @Bean
    public Binding chatBinding(Queue chatQueue, TopicExchange chatExchange) {
        return BindingBuilder
                .bind(chatQueue)
                .to(chatExchange)
                .with(CHAT_ROUTING_KEY);
    }

    // RabbitMQ와 메시지 담당할 클래스
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
        return new RabbitMessagingTemplate(rabbitTemplate);
    }

    // RabbitMQ와 연결 설정. CachingConnectionFactory를 선택
    @Bean
    public ConnectionFactory createConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(RABBITMQ_HOST);
        factory.setUsername("guest"); // RabbitMQ 관리자 아이디
        factory.setPassword("guest"); // RabbitMQ 관리자 비밀번호
        factory.setPort(5672); // RabbitMQ 연결할 port
        factory.setVirtualHost("/"); // vhost 지정

        return factory;
    }


    // 메시지를 JSON으로 직렬/역직렬화
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        // 교환기와 큐를 자동으로 생성
        admin.declareExchange(chatExchange());
        admin.declareQueue(chatQueue());
        admin.declareBinding(chatBinding(chatQueue(), chatExchange()));
        return admin;
    }
}