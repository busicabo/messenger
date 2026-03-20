package ru.mescat.message.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.mescat.keyvault.dto.PublicKey;
import ru.mescat.keyvault.dto.SaveDto;
import ru.mescat.keyvault.service.KeyVaultService;
import ru.mescat.message.dto.ApiResponse;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class CreateKeyVault {

    private KeyVaultService keyVaultService;
    private Integer maxActiveKey;

    public CreateKeyVault(KeyVaultService keyVaultService, @Value("${app.max-active-key}")Integer maxActiveKey){
        this.maxActiveKey=maxActiveKey;
        this.keyVaultService=keyVaultService;
    }

    public ApiResponse addNewKey(@RequestBody byte[] publicKey, Authentication authentication){
        Integer countAccounts = keyVaultService.getActiveCountPublicKey(authentication.getName());
        if(countAccounts==null){
            return new ApiResponse(1,"Не смогли получить существующие ключи",false, OffsetDateTime.now());
        }

        if(countAccounts>=maxActiveKey){
            return new ApiResponse(1,"Максимальное число активных сессий. Ограничьте доступ других ключей" +
                            ", после перезагрузите страницу."
                            ,false, OffsetDateTime.now());
        }
        UUID userId;
        try{
            userId = UUID.fromString(authentication.getName());
        }catch (IllegalArgumentException e){
            return new ApiResponse(1,"Ошибка установления личности. Пожалуйста попробуйте еще раз"
                            ,false, OffsetDateTime.now());
        }
        PublicKey pk = keyVaultService.saveKey(new SaveDto(userId,publicKey));
        if(pk==null){
            return new ApiResponse(1,"Не удалось создать новый ключ. Пожалуйста перезайдите в аккаунт!"
                            ,false, OffsetDateTime.now());
        }
        return new ApiResponse(0,"Успешное создания ключа! Можете начинать общение."
                        ,true, OffsetDateTime.now());
    }
}
