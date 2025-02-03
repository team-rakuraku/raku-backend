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
    // single, group이라 나눌 필요가 있나? topic, queue로 나누면 되는 거 아닌가?
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

    @Column(name = "count")
    private Integer count;

    @Builder
    public ChatRooms(Apps appId, String name, Type type, LocalDateTime createdAt, Integer count){
        this.appId = appId;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.count = count;
    }

    public void minusPerson(){
        this.count--;
    }

    public void addPerson(){
        this.count++;
    }
}
