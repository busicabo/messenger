package ru.mescat.info.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="users")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class UserEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name = "blocked", nullable = false)
    private boolean blocked;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name="avatar_url", nullable = false,insertable = false, updatable = false)
    private String avatarUrl;

    @Column(name = "online", nullable = false)
    private boolean online;

    public UserEntity(UUID id, String username, String password, boolean blocked, boolean online) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.blocked = blocked;
        this.online = online;
    }
}
