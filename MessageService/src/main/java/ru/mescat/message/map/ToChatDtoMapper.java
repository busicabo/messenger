package ru.mescat.message.map;

import org.springframework.stereotype.Component;
import ru.mescat.message.dto.ChatDto;
import ru.mescat.message.dto.ChatUserDto;
import ru.mescat.message.entity.ChatUserEntity;
import ru.mescat.message.entity.enums.ChatType;
import ru.mescat.message.service.ChatUserService;
import ru.mescat.user.dto.User;
import ru.mescat.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component

public class ToChatDtoMapper {

    private final UserService userService;
    private final ChatUserService chatUserService;

    public ToChatDtoMapper(UserService userService, ChatUserService chatUserService) {
        this.chatUserService = chatUserService;
        this.userService = userService;
    }

    public List<ChatDto> convert(List<ChatUserEntity> chatUserEntities, UUID noTargetUser) {
        List<ChatDto> chatDtos = new ArrayList<>();
        chatDtos.addAll(personalConvert(chatUserEntities, noTargetUser));
        chatDtos.addAll(groupConvert(chatUserEntities));
        return chatDtos;
    }

    public List<ChatDto> personalConvert(List<ChatUserEntity> chatUserEntities, UUID noTargetUser) {
        List<ChatUserEntity> personalChat = chatUserEntities.stream()
                .filter(c -> c.getChat().getChatType() == ChatType.PERSONAL)
                .toList();

        if (personalChat.isEmpty()) {
            return List.of();
        }

        List<Long> chatIds = personalChat.stream()
                .map(c -> c.getChat().getChatId())
                .toList();

        List<ChatUserDto> chatUserDtos = chatUserService.findAllChatUsersByChatIds(chatIds, noTargetUser);

        if (chatUserDtos.isEmpty()) {
            return List.of();
        }

        List<UUID> userIds = chatUserDtos.stream()
                .map(ChatUserDto::getUserId)
                .distinct()
                .toList();

        List<User> users = userService.findAllByIds(userIds);

        List<ChatDto> result = new ArrayList<>();

        for (ChatUserEntity chatUserEntity : personalChat) {
            Long currentChatId = chatUserEntity.getChat().getChatId();

            ChatUserDto chatUserDto = chatUserDtos.stream()
                    .filter(c -> Objects.equals(c.getChatId(), currentChatId))
                    .findFirst()
                    .orElse(null);

            if (chatUserDto == null) {
                continue;
            }

            User user = users.stream()
                    .filter(u -> Objects.equals(u.getId(), chatUserDto.getUserId()))
                    .findFirst()
                    .orElse(null);

            if (user == null) {
                continue;
            }

            result.add(new ChatDto(
                    currentChatId,
                    chatUserEntity.getChat().getChatType(),
                    user.getUsername(),
                    user.getAvatarUrl(),
                    user.isOnline(),
                    user.getId()
            ));
        }

        return result;
    }

    public List<ChatDto> groupConvert(List<ChatUserEntity> chatUserEntities) {
        return chatUserEntities.stream()
                .filter(c -> c.getChat().getChatType() == ChatType.GROUP)
                .map(c -> new ChatDto(
                        c.getChat().getChatId(),
                        c.getChat().getChatType(),
                        c.getChat().getTitle(),
                        c.getChat().getAvatarUrl()
                ))
                .toList();
    }
}