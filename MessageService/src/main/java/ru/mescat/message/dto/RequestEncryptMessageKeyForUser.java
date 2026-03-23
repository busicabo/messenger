package ru.mescat.message.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RequestEncryptMessageKeyForUser {
    private UUID userTarget;
    private byte[] key;
    private UUID publicKeyUser;
}