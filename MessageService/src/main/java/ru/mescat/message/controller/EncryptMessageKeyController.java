package ru.mescat.message.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mescat.message.dto.SendEncryptKeyDto;
import ru.mescat.message.dto.kafka.KeyDelete;
import ru.mescat.message.exception.ChatNotFoundException;
import ru.mescat.message.exception.SaveToDatabaseException;
import ru.mescat.message.exception.UserBlockedException;
import ru.mescat.message.service.DeleteSentKeysService;
import ru.mescat.message.service.SendMessageKeyService;

import java.util.List;
import java.util.UUID;

//Контроллер отвечающий за ключи шифрования сообщений.

@RestController
@RequestMapping("/api/encrypt_message_key")
public class EncryptMessageKeyController {

    private final DeleteSentKeysService deleteSentKeysService;
    private final SendMessageKeyService sendMessageKeyService;

    public EncryptMessageKeyController(DeleteSentKeysService deleteSentKeysService,
                                       SendMessageKeyService sendMessageKeyService) {
        this.sendMessageKeyService = sendMessageKeyService;
        this.deleteSentKeysService = deleteSentKeysService;
    }

    //Удалить ключ если прочитал
    @PostMapping("/delete")
    public ResponseEntity<?> deleteMessageKey(@RequestBody KeyDelete keyDelete) {
        try {
            deleteSentKeysService.addKeyInQueue(keyDelete);
            return ResponseEntity.ok("Ключи добавлены в очередь на удаление.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Не удалось добавить ключи в очередь.");
        }
    }

    //Отправить ключи всем пользователям
    @PostMapping("/send")
    public ResponseEntity<?> sendKeys(@RequestHeader("X-User-Id") UUID userId,
                                      @RequestBody SendEncryptKeyDto sendEncryptKeyDto) {
        try {
            sendMessageKeyService.sendEncryptKey(userId, sendEncryptKeyDto);
            return ResponseEntity.ok("Ключи успешно отправлены.");
        } catch (SaveToDatabaseException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        } catch (ChatNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (UserBlockedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}