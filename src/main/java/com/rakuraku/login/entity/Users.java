package com.rakuraku.login.entity;

import com.rakuraku.apps.entity.Apps;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appId")
    private Apps appId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "userId")
    private String userId;

    @Column(name = "profileImage")
    private String profileImage;

    @CreatedDate
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Builder
    public Users(Apps appId, String userId, String nickname, String profileImage, LocalDateTime createdAt){
        this.appId = appId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
        this.userId = userId;
    }

}
