package com.rakuraku.chatrooms.entity;

import com.rakuraku.apps.entity.Apps;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_rooms")
@NoArgsConstructor
@Getter
public class ChatRooms {
    public enum Type{
        GROUP, SINGLE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId")
    private Long id;

    @ManyToOne
    @JoinColumn(name="appId")
    private Apps appId;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Builder
    public ChatRooms(Apps appId, String name, Type type, LocalDateTime createdAt){
        this.appId = appId;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
    }
}
