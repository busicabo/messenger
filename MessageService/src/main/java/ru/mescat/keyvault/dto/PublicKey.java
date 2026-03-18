package ru.mescat.keyvault.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class PublicKey {

    private UUID id;
    private UUID userId;
    private byte[] key;
    private OffsetDateTime createdAt;

    public PublicKey(byte[] key, UUID userId){
        this.userId=userId;
        this.key=key;
    }
}
