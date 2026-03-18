package ru.mescat.user.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserProfile {
    private UUID id;
    private String username;
    private String password;
    private boolean blocked;
    private boolean online;
}
