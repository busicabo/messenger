package ru.mescat.message.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class DeleteMessageDto {
    private Long chatId;
    private Long messageId;
}
