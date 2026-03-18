package ru.mescat.keyvault.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.mescat.keyvault.dto.PublicKey;
import ru.mescat.keyvault.dto.SaveDto;

import java.util.List;
import java.util.UUID;

@Service
public class KeyVaultService {

    private RestClient restClient;

    public KeyVaultService(@Qualifier("key_vault") RestClient restClient){
        this.restClient = restClient;
    }

    public Integer getActiveCountPublicKey(String id){
        Integer count = restClient.get()
                .uri("/api/public_keys/count/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Integer.class);
        return count;
    }

    public List<PublicKey> getKeys(String id){
        List<PublicKey> keys = restClient.get()
                .uri("/api/public_keys/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<PublicKey>>() {});
        return keys;
    }

    public List<PublicKey> getKeys(List<UUID> ids){
        List<PublicKey> keys = restClient.post()
                .uri("/api/public_keys")
                .body(ids)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<PublicKey>>() {});
        return keys;
    }

    public PublicKey saveKey(SaveDto saveDto){
        PublicKey key = restClient.post()
                .uri("/api/public_keys/save")
                .body(saveDto)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(PublicKey.class);
        return key;
    }

    public void deleteKeyById(String keyId){
        restClient.post()
                .uri("/api/public_keys/delete")
                .body(keyId)
                .retrieve();
    }


}
