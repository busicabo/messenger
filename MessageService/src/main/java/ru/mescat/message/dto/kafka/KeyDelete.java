package ru.mescat.message.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class KeyDelete {
    private UUID keyId;
}
