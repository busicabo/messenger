package ru.mescat.message.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mescat.message.dto.ChatDto;
import ru.mescat.message.map.UserChatDtoMap;
import ru.mescat.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/message/api")
public class SearchController {

    private final UserService userService;

    public SearchController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/search_by_username")
    public ResponseEntity<List<ChatDto>> searchByUsername(@RequestParam String username){
        if(username == null || username.isBlank()){
            return ResponseEntity.ok(List.of());
        }

        return ResponseEntity.ok(UserChatDtoMap.convert(userService.findByUsernameContaining(username)));
    }
}