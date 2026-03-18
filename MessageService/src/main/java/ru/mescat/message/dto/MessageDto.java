package ru.mescat.message.dto;



import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MessageDto {
    private Long chatId;
    private byte[] message;
    private String encryptionName;
}
