package ru.mescat.message.security.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.mescat.message.security.dto.AuthResponse;
import ru.mescat.user.dto.User;
import ru.mescat.user.service.UserService;

import java.util.UUID;

@Service
public class RefreshService {

    private final JwtService jwtService;
    @Qualifier("user")
    private final UserService userService;
    private final BlackListTokens blackListTokens;

    public RefreshService(JwtService jwtService, UserService userService, BlackListTokens blackListTokens) {
        this.blackListTokens = blackListTokens;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public AuthResponse refresh(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("Refresh token отсутствует");
        }

        if (!jwtService.isTokenSignatureValid(refreshToken)) {
            throw new IllegalArgumentException("Невалидный refresh token");
        }

        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Это не refresh token");
        }

        if (jwtService.isTokenExpired(refreshToken)) {
            throw new IllegalArgumentException("Refresh token истёк");
        }

        if (!blackListTokens.isValid(refreshToken)) {
            throw new IllegalArgumentException("Refresh token отозван");
        }

        UUID userId = jwtService.extractUserId(refreshToken);
        User user = userService.findById(userId);

        if (user == null || user.isBlocked()) {
            throw new IllegalArgumentException("Пользователь недоступен");
        }

        blackListTokens.initIfAbsent(user.getId().toString());

        String newAccessToken = jwtService.generateAccessToken(user.getId());
        String newRefreshToken = jwtService.generateRefreshToken(user.getId());

        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}
