package ru.mescat.info.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCover {
    private UUID id;
    private String username;
    private String avatarUrl;
    private boolean online;
}
