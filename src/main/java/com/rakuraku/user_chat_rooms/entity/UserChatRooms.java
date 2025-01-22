package com.rakuraku.user_chat_rooms.entity;

import com.rakuraku.chatrooms.entity.ChatRooms;
import com.rakuraku.login.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Table(name = "user_chat_rooms")
@Entity
@NoArgsConstructor
public class UserChatRooms {

    public enum Role {
        MEMBER, ADMIN // 역할을 정의
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_chat_rooms_id")
    private Long id; // 기본 키

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false) // 외래 키 설정
    private Users user;

    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false) // 외래 키 설정
    private ChatRooms chatRoom;

    @Column(name = "joinedAt", nullable = false)
    private LocalDateTime joinedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Builder
    public UserChatRooms(Users user, ChatRooms chatRoom, LocalDateTime joinedAt, Role role) {
        this.user = user;
        this.chatRoom = chatRoom;
        this.joinedAt = joinedAt;
        this.role = role;
    }
}
