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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId")
    private Long id;

    @ManyToOne
    @JoinColumn(name="appId")
    private Apps appId;

    @Column(name = "name")
    private String name;


    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "count")
    private Integer count;

    @Builder
    public ChatRooms(Apps appId, String name,  LocalDateTime createdAt, Integer count){
        this.appId = appId;
        this.name = name;
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
