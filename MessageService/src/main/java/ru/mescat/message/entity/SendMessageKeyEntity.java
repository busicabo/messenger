package ru.mescat.message.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "send_message_keys")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageKeyEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "key", nullable = false)
    private byte[] key;

    @Column(name = "public_key", nullable = false)
    private UUID publicKey;

    @Column(name = "user_target_id")
    private UUID userTargetId;

    @Column(name = "send_at", nullable = false,insertable = false, updatable = false)
    private OffsetDateTime sendAt;
}
