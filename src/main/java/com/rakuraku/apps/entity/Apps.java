package com.rakuraku.apps.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "apps")
@Getter
@NoArgsConstructor
public class Apps {
    // rakuraku1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "appId")
    private String appId;

    @Column(name = "name")
    private String name;

    @Column(name = "createdAt")
    @CreatedDate // 생성할 때 시간 설정
    private LocalDateTime createdAt;

    @Builder
    public Apps(String name, String appId ,LocalDateTime createdAt){
        this.appId = appId;
        this.name = name;
        this.createdAt = createdAt;
    }

}
