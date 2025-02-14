package com.rakuraku.stomp.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Getter
@ToString
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat")
public class MongoMessage {
    @Id
    private String id;
    private String roomId;
    private String userId;
    private String content;
    private Date time;
    private String appId;
    private String cloudFrontImageURL = "null";
}