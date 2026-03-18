package ru.mescat.message.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mescat.message.entity.SendMessageKeyEntity;
import ru.mescat.message.repository.SendMessageKeyRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class SendMessageKeyService {

    private final SendMessageKeyRepository sendMessageKeyRepository;

    public SendMessageKeyService(SendMessageKeyRepository sendMessageKeyRepository) {
        this.sendMessageKeyRepository = sendMessageKeyRepository;
    }

    @Transactional
    public SendMessageKeyEntity save(SendMessageKeyEntity entity) {
        return sendMessageKeyRepository.save(entity);
    }

    @Transactional
    public List<SendMessageKeyEntity> saveAll(List<SendMessageKeyEntity> entities) {
        return sendMessageKeyRepository.saveAll(entities);
    }

    public SendMessageKeyEntity findById(UUID id) {
        return sendMessageKeyRepository.findById(id).orElse(null);
    }

    public List<SendMessageKeyEntity> findAll() {
        return sendMessageKeyRepository.findAll();
    }

    public List<SendMessageKeyEntity> findAllByUserId(UUID userId) {
        return sendMessageKeyRepository.findAllByUserId(userId);
    }

    public List<SendMessageKeyEntity> findAllByUserTargetId(UUID userTargetId) {
        return sendMessageKeyRepository.findAllByUserTargetId(userTargetId);
    }

    public SendMessageKeyEntity findByUserIdAndUserTargetId(UUID userId, UUID userTargetId) {
        return sendMessageKeyRepository.findByUserIdAndUserTargetId(userId, userTargetId).orElse(null);
    }

    public List<SendMessageKeyEntity> findAllByPublicKey(UUID publicKey) {
        return sendMessageKeyRepository.findAllByPublicKey(publicKey);
    }

    public boolean existsByUserIdAndUserTargetId(UUID userId, UUID userTargetId) {
        return sendMessageKeyRepository.existsByUserIdAndUserTargetId(userId, userTargetId);
    }

    @Transactional
    public void deleteById(UUID id) {
        sendMessageKeyRepository.deleteById(id);
    }

    @Transactional
    public void deleteByUserIdAndUserTargetId(UUID userId, UUID userTargetId) {
        sendMessageKeyRepository.deleteByUserIdAndUserTargetId(userId, userTargetId);
    }
}