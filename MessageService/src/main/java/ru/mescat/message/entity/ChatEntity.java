package ru.mescat.message.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mescat.message.entity.enums.ChatType;

import java.time.OffsetDateTime;

@Entity
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    @Enumerated(EnumType.STRING)
    @Column(name = "chat_type", nullable = false)
    private ChatType chatType;

    @Column(name = "created_at", nullable = false,insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "title")
    private String title;

    @Column(name="avatar_url",nullable = false,insertable = false, updatable = false)
    private String avatarUrl;

    public ChatEntity(ChatType type, String title){
        this.chatId = chatId;
        this.chatType=type;
        this.title=title;
    }

    public ChatEntity(ChatType type){
        this.chatType=type;
    }
}
