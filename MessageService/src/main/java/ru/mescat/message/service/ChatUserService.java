package ru.mescat.message.service;


import org.springframework.stereotype.Service;
import ru.mescat.message.dto.auxiliary.ChatUserDto;
import ru.mescat.message.entity.ChatEntity;
import ru.mescat.message.entity.ChatUserEntity;
import ru.mescat.message.entity.enums.ChatType;
import ru.mescat.message.repository.ChatUserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ChatUserService {
    private ChatUserRepository repository;

    public ChatUserService(ChatUserRepository repository){
        this.repository=repository;
    }

    public ChatUserEntity save(ChatUserEntity chatUserEntity) {
        return repository.save(chatUserEntity);
    }

    public ChatUserEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ChatUserEntity> findAllByUserId(UUID userId) {
        return repository.findAllByUserId(userId);
    }


    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public boolean existsByChatIdAndUserId(Long chatId, UUID userId) {
        return repository.existsByChat_ChatIdAndUserId(chatId, userId);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<ChatUserDto> findAllChatUsersByChatIds(List<Long> chatIds,UUID noTargetUser){
        return repository.findAllChatUsersByChatIds(chatIds, noTargetUser);
    }

    public ChatEntity findPersonalBetween(UUID user1, UUID user2, ChatType chatType){
        return repository.findPersonalChatBetween(user1,user2,chatType);
    }

    public List<UUID> findAllUserIdNotBlocksByChatId(Long chatId){
        return repository.findAllUserIdNotBlocksByChatId(chatId);
    }

    public List<ChatUserEntity> findAllNotBlocksByChatId(Long chatId){
        return repository.findAllNotBlocksByChatId(chatId);
    }

}
