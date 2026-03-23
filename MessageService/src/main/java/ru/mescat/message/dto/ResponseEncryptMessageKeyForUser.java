package ru.mescat.message.dto;

import lombok.*;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ResponseEncryptMessageKeyForUser {
    private UUID userTarget;
    private byte[] key;
    private String encryptName;
    private UUID publicKeyUser;
}
