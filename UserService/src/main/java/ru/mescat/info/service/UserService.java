package ru.mescat.info.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mescat.info.dto.UserCover;
import ru.mescat.info.entity.UserEntity;
import ru.mescat.info.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity save(UserEntity user){
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> findById(UUID id){
        return userRepository.findById(id);
    }

    public void delete(UUID id){
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> searchByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<UserEntity> findByUsernameContaining(String x){
        return userRepository.findByUsernameContainingIgnoreCase(x);
    }

    public boolean updatePassword(UUID userId, String password){
        return userRepository.updatePasswordById(userId, passwordEncoder.encode(password)) == 1;
    }

    public boolean updateBlocked(UUID userId, boolean blocked){
        return userRepository.updateBlockedById(userId, blocked) == 1;
    }

    public boolean updateOnline(UUID userId, boolean online){
        return userRepository.updateOnlineById(userId, online) == 1;
    }

    public List<UserEntity> findAllByIds(List<UUID> userIds){
        return userRepository.findAllById(userIds);
    }

    public boolean updateUsername(UUID userId, String username){
        return userRepository.updateUsernameById(userId, username) == 1;
    }

    public UserEntity findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    public UUID getIdByUsername(String username){
        return userRepository.getIdByUsername(username);
    }

    public UserEntity createNewUser(String username, String password){
        UserEntity entity = new UserEntity(
                username,
                passwordEncoder.encode(password)
        );

        return save(entity);
    }
}