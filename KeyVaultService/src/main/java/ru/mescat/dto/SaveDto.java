package ru.mescat.dto;

import jakarta.persistence.Column;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SaveDto {
    private UUID userId;
    private byte[] key;
}
