package com.rakuraku.messages.entity;

import com.rakuraku.apps.entity.Apps;
import com.rakuraku.chatrooms.entity.ChatRooms;
import com.rakuraku.login.entity.Users;
import com.rakuraku.user_chat_rooms.entity.UserChatRooms;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "messages")
@Getter
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private ChatRooms roomId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "appId")
    private Apps appId;

    @Column(name = "content")
    private String content;

    @Column(name = "mediaUrl")
    private String mediaUrl;

    @Column(name ="mediaType")
    private String meidaType;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Builder
    public Messages(ChatRooms roomId, Users userId, Apps appId, String content, String mediaUrl, String meidaType, LocalDateTime timestamp){
        this.appId = appId;
        this.roomId = roomId;
        this.userId = userId;
        this.meidaType = meidaType;
        this.content = content;
        this.timestamp = timestamp;
        this.mediaUrl = mediaUrl;
    }
}
