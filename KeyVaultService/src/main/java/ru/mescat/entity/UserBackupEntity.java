package ru.mescat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_backup")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserBackupEntity {

    @Id
    @Column(name = "backup_id", nullable = false, updatable = false)
    private UUID backupId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "data", nullable = false, columnDefinition = "jsonb")
    private String data;

    public UserBackupEntity(UUID backupId, UUID userId, String data) {
        this.backupId = backupId;
        this.userId = userId;
        this.data = data;
    }

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

}