package ru.mescat.message.security;

import java.security.Principal;
import java.util.UUID;

public record JwtPrincipal(UUID userId) implements Principal {

    @Override
    public String getName() {
        return userId.toString();
    }
}
