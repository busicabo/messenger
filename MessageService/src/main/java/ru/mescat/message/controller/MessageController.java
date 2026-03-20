package ru.mescat.message.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mescat.message.dto.MessageDto;
import ru.mescat.message.entity.MessageEntity;
import ru.mescat.message.map.MessageEntityMessageDtoMapper;
import ru.mescat.message.service.MessageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/message/api")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService=messageService;
    }

    @GetMapping("/getLastMessages/{count}")
    public ResponseEntity<List<MessageDto>> getLastMessage(@PathVariable Integer count){
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        if(count==null){
            return ResponseEntity.notFound().build();
        }
        List<MessageEntity> messages = messageService.getLastNMessagesForEachUserChat(userId, count);
        if (messages==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(messages.stream().map(MessageEntityMessageDtoMapper::convert).toList());
    }

    @GetMapping("/getMessageInChatWithLimit/{chatId}/{count}")
    public ResponseEntity<List<MessageEntity>> getMessageInChatWithLimit(@PathVariable Long messageId, @PathVariable Integer count){
        if(messageId==null || count==null){
            return ResponseEntity.notFound().build();
        }
        List<MessageEntity> messages = messageService.getMessagesRelativeToMessage(messageId, count);
        if (messages==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(messages);
    }


}
