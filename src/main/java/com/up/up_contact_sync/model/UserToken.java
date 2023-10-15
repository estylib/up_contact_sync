package com.up.up_contact_sync.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
@Data
@Builder
@Document("user_token")
public class UserToken {
    @Id
    private String id;
    @Indexed
    @Field("username")
    private String username;
    @Indexed
    @Field("token")
    private String token;
    @Field("used")
    private boolean used;
    private Instant expiredTokenDate;
}
