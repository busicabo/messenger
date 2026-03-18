package ru.mescat.info.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Table(name = "user_settings")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSettingsEntity {

    @Id
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UUID user_id;

    @Column(name = "auto_delete_message")
    private OffsetDateTime autoDeleteMessage;

    @Column(name = "allow_writing", nullable = false)
    private boolean allowWriting;

    @Column(name = "allow_add_chat", nullable = false)
    private boolean allowAddChat;
}
