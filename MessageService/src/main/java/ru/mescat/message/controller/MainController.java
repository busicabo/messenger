package ru.mescat.message.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@Controller
public class MainController {

    @GetMapping("/message")
    public String message() {
        return "chat";
    }

    @GetMapping("/getId")
    public ResponseEntity<UUID> getId(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(userId);
    }
}