package ru.mescat.message.dto.kafka;

import tools.jackson.databind.JsonNode;

public class MessageEventDto {
    private MessageEventType type;
    private JsonNode payload;
}
