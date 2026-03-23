package ru.mescat.message.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.mescat.keyvault.dto.PublicKey;
import ru.mescat.keyvault.dto.UserPublicKeyDto;
import ru.mescat.keyvault.service.KeyVaultService;
import ru.mescat.message.dto.ApiResponse;
import ru.mescat.message.map.PublicKeyUserPublicKeyDtoMapper;
import ru.mescat.message.service.CreateKeyVault;
import ru.mescat.message.service.DeleteSentKeysService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/message/api/encrypt_key")
public class EncryptKeyController {

    private KeyVaultService keyVaultService;
    private Integer maxActiveKey;
    private CreateKeyVault createKeyVault;

    public EncryptKeyController(KeyVaultService keyVaultService,
                                CreateKeyVault createKeyVault){
        this.createKeyVault=createKeyVault;
        this.keyVaultService=keyVaultService;
    }

    @PostMapping("/new_key")
    public ResponseEntity<ApiResponse> addNewKey(@RequestBody byte[] publicKey, Authentication authentication){
        return ResponseEntity.ok(createKeyVault.addNewKey(publicKey,authentication));
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
