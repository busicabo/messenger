package ru.mescat.message.dto;

import java.util.UUID;

public class NewMessageToNewChat {
    private UUID userId;
    private byte[] message;
    private String keyName;
}
