package ru.mescat.message.map;

import ru.mescat.keyvault.dto.PublicKey;
import ru.mescat.keyvault.dto.UserPublicKeyDto;

import java.util.ArrayList;
import java.util.List;

public class PublicKeyUserPublicKeyDtoMapper {
    public static List<UserPublicKeyDto> convert(List<PublicKey> publicKeys){
        List<UserPublicKeyDto> result = new ArrayList<>();
        publicKeys.forEach(k -> result.add(new UserPublicKeyDto(k.getUserId(),k.getKey())));
        return result;
    }
}
