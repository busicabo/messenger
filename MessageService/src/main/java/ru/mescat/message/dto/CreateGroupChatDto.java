package ru.mescat.message.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class CreateGroupChatDto {
    private String title;
    private String avatarUrl;
}
