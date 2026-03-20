package ru.mescat.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mescat.message.entity.ChatEntity;
import ru.mescat.message.entity.enums.ChatType;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    @Query("""
            select cu1.chat
            from ChatUserEntity cu1
            join ChatUserEntity cu2 on cu1.chat.chatId = cu2.chat.chatId
            where cu1.userId = :currentUserId
              and cu2.userId = :targetUserId
              and cu1.chat.chatType = :chatType
            """)
    ChatEntity findPersonalChatBetween(
            @Param("currentUserId") UUID currentUserId,
            @Param("targetUserId") UUID targetUserId,
            @Param("chatType") ChatType chatType
    );









}