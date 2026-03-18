package ru.mescat.message.service;

import org.springframework.stereotype.Service;
import ru.mescat.message.dto.auxiliary.ChatIdLastMessageEncryptName;
import ru.mescat.message.entity.ChatEntity;
import ru.mescat.message.entity.enums.ChatType;
import ru.mescat.message.repository.ChatRepository;
import ru.mescat.message.repository.MessageRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {
    private ChatRepository repository;
    private MessageRepository messageRepository;

    public ChatService(ChatRepository repository, MessageRepository messageRepository){
        this.messageRepository=messageRepository;
        this.repository=repository;
    }


    public ChatEntity save(ChatEntity chat) {
        return repository.save(chat);
    }

    public ChatEntity findById(Long chatId) {
        return repository.findById(chatId).orElse(null);
    }

    public List<ChatEntity> findAll() {
        return repository.findAll();
    }

    public boolean existsById(Long chatId) {
        return repository.existsById(chatId);
    }

    public void deleteById(Long chatId) {
        repository.deleteById(chatId);
    }

    public ChatEntity findPersonalChatBetween(UUID currentUserId, UUID targetUserId){
        return repository.findPersonalChatBetween(currentUserId,targetUserId, ChatType.PERSONAL);
    }


}
