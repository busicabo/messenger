package ru.mescat.user.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserSettings {
    private UUID user_id;
    private OffsetDateTime autoDeleteMessage;
    private boolean allowWriting;
    private boolean allowAddChat;
}
