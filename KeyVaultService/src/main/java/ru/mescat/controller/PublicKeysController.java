package ru.mescat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mescat.dto.SaveDto;
import ru.mescat.entity.PublicKeyEntity;
import ru.mescat.service.PublicKeyService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public_keys")
public class PublicKeysController {

    private PublicKeyService publicKeyService;

    public PublicKeysController(PublicKeyService service){
        this.publicKeyService=service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PublicKeyEntity>> get(@PathVariable UUID id){
        return ResponseEntity.ok(publicKeyService.findAllById(id));
    }

    @PostMapping("/")
    public ResponseEntity<List<PublicKeyEntity>> get(@RequestBody List<UUID> userIds){
        return ResponseEntity.ok(publicKeyService.findAllById(userIds));
    }

    @GetMapping("/count/{id}")
    public ResponseEntity<Integer> getCountPublicKeysByUserId(@PathVariable UUID id){
        return ResponseEntity.ok(publicKeyService.getCountPublicKeysByUserId(id));
    }

    @PostMapping("/save")
    public ResponseEntity<PublicKeyEntity> save(@RequestBody SaveDto saveDto){
        if(saveDto.getKey()==null || saveDto.getUserId()==null){
            return ResponseEntity.ok(null);
        }
        PublicKeyEntity publicKeyEntity = new PublicKeyEntity();
        publicKeyEntity.setKey(saveDto.getKey());
        publicKeyEntity.setUserId(saveDto.getUserId());
        return ResponseEntity.ok(publicKeyService.save(publicKeyEntity));
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody UUID id){
        publicKeyService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
