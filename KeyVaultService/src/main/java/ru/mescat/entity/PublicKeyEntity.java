package ru.mescat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "public_keys")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PublicKeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "key", nullable = false)
    private byte[] key;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    public PublicKeyEntity(UUID userId, byte[] key) {
        this.userId = userId;
        this.key = key;
    }
}