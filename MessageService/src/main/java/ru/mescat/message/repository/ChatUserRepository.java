package ru.mescat.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mescat.message.dto.ChatUserDto;
import ru.mescat.message.entity.ChatEntity;
import ru.mescat.message.entity.ChatUserEntity;

import java.util.List;
import java.util.UUID;

public interface ChatUserRepository extends JpaRepository<ChatUserEntity, Long> {
    List<ChatUserEntity> findAllByUserId(UUID userId);

    @Query("""
            select cu.chat
            from ChatUserEntity cu
            where cu.userId = :userId
            """)
    List<ChatEntity> findAllChatsByUserId(@Param("userId") UUID userId);

    boolean existsByChat_ChatIdAndUserId(Long chatId, UUID userId);

    @Query("""
        select distinct new ru.mescat.message.dto.ChatUserDto(cu.userId, cu.chat.chatId)
        from ChatUserEntity cu
        where cu.chat.chatId in :chatIds
          and cu.userId <> :noTarget
        """)
    List<ChatUserDto> findAllChatUsersByChatIds(
            @Param("chatIds") List<Long> chatIds,
            @Param("noTarget") UUID noTarget
    );
}