package ru.mescat.keyvault.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SaveDto {
    private UUID userId;
    private byte[] key;
}
