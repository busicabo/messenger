package ru.mescat.user.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User{
    private UUID id;
    private String username;
    private String password;
    private boolean blocked;
    private OffsetDateTime createdAt;
    private String avatarUrl;
    private boolean online;
}
