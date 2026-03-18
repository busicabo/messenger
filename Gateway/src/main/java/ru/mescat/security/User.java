package ru.mescat.security;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String password;
    private boolean blocked;
    private OffsetDateTime createdAt;
    private boolean online;
}
