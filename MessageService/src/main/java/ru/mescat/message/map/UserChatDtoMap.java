package ru.mescat.message.map;


import org.springframework.security.core.context.SecurityContextHolder;
import ru.mescat.message.dto.ChatDto;
import ru.mescat.message.entity.ChatEntity;
import ru.mescat.message.entity.MessageEntity;
import ru.mescat.message.entity.enums.ChatType;
import ru.mescat.message.exception.RemoteServiceException;
import ru.mescat.message.service.ChatService;
import ru.mescat.message.service.ChatUserService;
import ru.mescat.message.service.MessageService;
import ru.mescat.user.dto.User;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserChatDtoMap {

    private ChatService chatService;
    private MessageService messageService;

    public UserChatDtoMap(ChatService chatService, MessageService messageService){
        this.chatService=chatService;
        this.messageService=messageService;
    }

    public  ChatDto convert(User user){
        UUID userId;
        UUID userTarget;
        try{
            userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
            userTarget = user.getId();
        } catch (IllegalArgumentException e){
            throw new RemoteServiceException(1,"Данные не соответствуют нужному типу!");
        }

        ChatEntity chat = chatService.findPersonalChatBetween(userId,userTarget);
        if(chat==null){
            return new ChatDto(ChatType.PERSONAL,chat.getTitle(),chat.getAvatarUrl());
        }
        ChatDto chatDto = new ChatDto(chat.getChatId(),chat.getChatType(),user.getUsername(),user.getAvatarUrl());

        MessageEntity message = messageService.findLatestMessageByChatId(chat.getChatId());

        if(message!=null){
            chatDto.setLastMessage(message.getMessage());
            chatDto.setEncryptName(message.getEncryptionName());
        }

        return chatDto;

    }

    public List<ChatDto> convert(List<User> users){
        List<UUID>
    }
}
