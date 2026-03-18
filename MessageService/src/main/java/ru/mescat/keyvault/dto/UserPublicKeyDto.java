package ru.mescat.keyvault.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPublicKeyDto {
    private UUID user;
    private byte[] publicKey;
}
