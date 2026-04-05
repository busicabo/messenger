package ru.mescat.message.dto.kafka;

import tools.jackson.databind.JsonNode;

public class ChatEventDto {
    private ChatEventType type;
    private JsonNode payload;
}