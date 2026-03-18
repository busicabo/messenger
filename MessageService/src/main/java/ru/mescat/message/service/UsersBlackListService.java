package ru.mescat.message.service;

import org.springframework.stereotype.Service;
import ru.mescat.message.entity.UsersBlackListEntity;
import ru.mescat.message.repository.UsersBlackListRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersBlackListService {

    private final UsersBlackListRepository repository;

    public UsersBlackListService(UsersBlackListRepository repository) {
        this.repository = repository;
    }

    public UsersBlackListEntity save(UsersBlackListEntity entity) {
        return repository.save(entity);
    }

    public UsersBlackListEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean isBlockedInChat(Long chatId, UUID userTarget) {
        return repository.existsByChat_ChatIdAndUserTarget(chatId, userTarget);
    }

    public boolean isBlockedByUserInChat(UUID userInitiator, Long chatId, UUID userTarget) {
        return repository.existsByUserInitiatorAndChat_ChatIdAndUserTarget(userInitiator, chatId, userTarget);
    }

    public List<UsersBlackListEntity> getAllUserBlocks(UUID userInitiator) {
        return repository.findAllByUserInitiator(userInitiator);
    }

    public List<UsersBlackListEntity> getAllBlocksOfTargetUser(UUID userTarget) {
        return repository.findAllByUserTarget(userTarget);
    }

    public List<UsersBlackListEntity> getAllChatBlocks(Long chatId) {
        return repository.findAllByChat_ChatId(chatId);
    }

    public Optional<UsersBlackListEntity> findBlock(UUID userInitiator, Long chatId, UUID userTarget) {
        return repository.findByUserInitiatorAndChat_ChatIdAndUserTarget(userInitiator, chatId, userTarget);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void unblock(UUID userInitiator, Long chatId, UUID userTarget) {
        repository.deleteByUserInitiatorAndChat_ChatIdAndUserTarget(userInitiator, chatId, userTarget);
    }
}