package ru.mescat.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mescat.info.entity.UserSettingsEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface UserSettingsRepository extends JpaRepository<UserSettingsEntity, UUID> {

    @Modifying
    @Query(value = """
            UPDATE user_settings
            SET auto_delete_message = :time
            WHERE user_id = :user_id
            """,nativeQuery = true)
    int setAutoDeleteMessage(@Param("time")OffsetDateTime time, @Param("user_id") UUID userId);

    @Modifying
    @Query(value = """
            UPDATE user_settings
            SET allow_writing = :write
            WHERE user_id = :user_id
            """,nativeQuery = true)
    int setAllowWriting(@Param("write")boolean write, @Param("user_id") UUID userId);

    @Modifying
    @Query(value = """
            UPDATE user_settings
            SET allow_add_chat = :chat
            WHERE user_id = :user_id
            """,nativeQuery = true)
    int setAllowAddChat(@Param("chat")boolean chat, @Param("user_id") UUID userId);

}
