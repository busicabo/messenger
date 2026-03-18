package ru.mescat.info.service;

import org.springframework.stereotype.Service;
import ru.mescat.info.entity.UserSettingsEntity;
import ru.mescat.info.repository.UserSettingsRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class UserSettingsService {
    private UserSettingsRepository repository;

    public UserSettingsService(UserSettingsRepository repository){
        this.repository=repository;
    }

    public UserSettingsEntity findById(UUID userId){
        return repository.findById(userId).orElse(null);
    }

    public UserSettingsEntity save(UserSettingsEntity entity){
        return repository.save(entity);
    }

    public boolean setAutoDeleteMessage(OffsetDateTime time, UUID userId){
        return repository.setAutoDeleteMessage(time,userId)==1;
    }

    public boolean setAllowWriting(boolean bol, UUID userId){
        return repository.setAllowWriting(bol, userId)==1;
    }

    public boolean setAllowAddChat(boolean bol, UUID userId){
        return repository.setAllowAddChat(bol, userId)==1;
    }
}
