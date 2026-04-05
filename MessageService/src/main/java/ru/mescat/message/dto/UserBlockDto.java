package ru.mescat.message.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserBlockDto {
    private Long chatId;
    private UUID userId;
}
