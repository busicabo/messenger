package ru.mescat.message.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.mescat.keyvault.dto.PublicKey;
import ru.mescat.keyvault.dto.SaveDto;
import ru.mescat.keyvault.dto.UserPublicKeyDto;
import ru.mescat.keyvault.service.KeyVaultService;
import ru.mescat.message.dto.ApiResponse;
import ru.mescat.message.map.PublicKeyUserPublicKeyDtoMapper;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/message/api")
public class KeyController {

    private KeyVaultService keyVaultService;
    private Integer maxActiveKey;

    public KeyController(KeyVaultService keyVaultService, @Value("${app.max-active-key}")Integer maxActiveKey){
        this.maxActiveKey=maxActiveKey;
        this.keyVaultService=keyVaultService;
    }

    @PostMapping("/new_key")
    public ResponseEntity<ApiResponse> addNewKey(@RequestBody byte[] publicKey, Authentication authentication){
        Integer countAccounts = keyVaultService.getActiveCountPublicKey(authentication.getName());
        if(countAccounts==null){
            return ResponseEntity.ok(
                    new ApiResponse(1,"Не смогли получить существующие ключи",false, OffsetDateTime.now()));
        }

        if(countAccounts>=maxActiveKey){
            return ResponseEntity.ok(
                    new ApiResponse(1,"Максимальное число активных сессий. Ограничьте доступ других ключей" +
                            ", после перезагрузите страницу."
                            ,false, OffsetDateTime.now()));
        }
        UUID userId;
        try{
            userId = UUID.fromString(authentication.getName());
        }catch (IllegalArgumentException e){
                return ResponseEntity.ok(
                        new ApiResponse(1,"Ошибка установления личности. Пожалуйста попробуйте еще раз"
                                ,false, OffsetDateTime.now()));
        }
        PublicKey pk = keyVaultService.saveKey(new SaveDto(userId,publicKey));
        if(pk==null){
            return ResponseEntity.ok(
                    new ApiResponse(1,"Не удалось создать новый ключ. Пожалуйста перезайдите в аккаунт!"
                            ,false, OffsetDateTime.now()));
        }
        return ResponseEntity.ok(
                new ApiResponse(0,"Успешное создания ключа! Можете начинать общение."
                        ,true, OffsetDateTime.now()));
    }

    @PostMapping("/")
    public ResponseEntity<List<UserPublicKeyDto>> getAllKeys(@RequestBody List<UUID> uuids){
        List<PublicKey> keys = keyVaultService.getKeys(uuids);
        if(keys==null){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(PublicKeyUserPublicKeyDtoMapper.convert(keys));
    }
}
