package ru.mescat.message.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.mescat.message.dto.ApiResponse;
import ru.mescat.message.entity.ChatEntity;
import ru.mescat.message.entity.ChatUserEntity;
import ru.mescat.message.entity.enums.ChatType;
import ru.mescat.message.map.ToChatDtoMapper;
import ru.mescat.message.service.ChatService;
import ru.mescat.message.service.ChatUserService;
import ru.mescat.message.websocket.WebSocketService;
import ru.mescat.user.dto.User;
import ru.mescat.user.service.UserService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/message/api")
public class ChatController {

    private ChatService chatService;
    private ChatUserService chatUserService;
    private UserService userService;
    private WebSocketService webSocketService;
    private ToChatDtoMapper toChatDtoMapper;

    public ChatController(ChatService chatService,
                          ChatUserService chatUserService,
                          UserService userService,
                          WebSocketService webSocketService,
                          ToChatDtoMapper toChatDtoMapper){
        this.toChatDtoMapper=toChatDtoMapper;
        this.webSocketService=webSocketService;
        this.userService=userService;
        this.chatService=chatService;
        this.chatUserService=chatUserService;
    }

    @PostMapping("/personalChat/findOrCreate")
    public ResponseEntity<?> findOrCreatePersonalChat(@RequestBody ChatDto chatDto,
                                                      Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        UUID targetUserId = chatDto.getUserId();

        if (currentUserId.equals(targetUserId)) {
            return ResponseEntity.ok(
                    new ApiResponse(1, "Нельзя создать чат с самим собой!", false, OffsetDateTime.now())
            );
        }

        ChatEntity existingChat = chatService.findPersonalChatBetween(currentUserId, targetUserId);
        if (existingChat != null) {
            User user = userService.findById(targetUserId);
            ChatDto existingChatDto = new ChatDto(existingChat.getChatId(), existingChat.getChatType(),
                    user.getUsername(), user.getAvatarUrl(),user.isOnline(),user.getId());
            return ResponseEntity.ok(existingChatDto);
        }

        ChatEntity chat = chatService.save(new ChatEntity(ChatType.PERSONAL));
        if (chat == null) {
            return ResponseEntity.ok(
                    new ApiResponse(1, "Не получилось создать чат. Попробуйте еще раз!", false, OffsetDateTime.now())
            );
        }

        ChatUserEntity entity1 = chatUserService.save(new ChatUserEntity(chat, targetUserId));
        if (entity1 == null) {
            chatService.deleteById(chat.getChatId());
            return ResponseEntity.ok(
                    new ApiResponse(1, "Не удалось добавить пользователя!", false, OffsetDateTime.now())
            );
        }

        ChatUserEntity entity2 = chatUserService.save(new ChatUserEntity(chat, currentUserId));
        if (entity2 == null) {
            chatService.deleteById(chat.getChatId());
            return ResponseEntity.ok(
                    new ApiResponse(1, "Не удалось добавить пользователя!", false, OffsetDateTime.now())
            );
        }

        ChatDto result = new ChatDto(
                chat.getChatId(),
                ChatType.PERSONAL,
                chatDto.getTitle(),
                chatDto.getAvatarUrl(),
                chatDto.getOnline(),
                targetUserId
        );

        return ResponseEntity.ok(result);
    }

    @GetMapping("/chats")
    public ResponseEntity<List<ChatDto>> getChats(Authentication authentication){
        UUID userId = UUID.fromString(authentication.getName());
        List<ChatUserEntity> chats = chatUserService.findAllByUserId(userId);
        if(chats==null){
            webSocketService.sendNotification(
                    new ApiResponse(1,"Не удалось загрузить чаты!",false,OffsetDateTime.now()),userId);
            return null;
        }

        List<ChatDto> chatDtos = toChatDtoMapper.personalConvert(chats,userId);
        if(chatDtos==null){
            webSocketService.sendNotification(
                    new ApiResponse(1,"Не удалось загрузить чаты!",false,OffsetDateTime.now()),userId);
            return null;
        }

        return ResponseEntity.ok(chatDtos);
    }

}
