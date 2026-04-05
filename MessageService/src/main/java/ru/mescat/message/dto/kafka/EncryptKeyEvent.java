package ru.mescat.message.dto.kafka;

import tools.jackson.databind.JsonNode;

public class EncryptKeyEvent {
    private EncryptKeyType type;
    private JsonNode payload;
}
