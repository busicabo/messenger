package ru.mescat.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mescat.auth.dto.RegDto;
import ru.mescat.info.entity.UserEntity;
import ru.mescat.info.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        UserEntity user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(404).body("Пользователь не найден");
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/info/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        return userService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Пользователь не найден"));
    }

    @PostMapping("/reg")
    public ResponseEntity<?> reg(@RequestBody RegDto regDto) {
        UserEntity entity = userService.findByUsername(regDto.getUsername());

        if (entity != null) {
            return ResponseEntity.status(409).body("Пользователь уже существует");
        }

        entity = userService.createNewUser(regDto.getUsername(), regDto.getPassword());

        if (entity == null) {
            return ResponseEntity.status(500).body("Не удалось создать новый аккаунт");
        }

        return ResponseEntity.ok(entity);
    }
}
