package ru.mescat.message.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ChatUserDto {
    private UUID userId;
    private Long chatId;
}
