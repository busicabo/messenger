package ru.mescat.security.service;

import org.springframework.stereotype.Service;
import ru.mescat.security.User;
import ru.mescat.security.dto.AuthResponse;
import ru.mescat.security.dto.RegDto;

@Service
public class RegisterService {

    private final JwtService jwtService;
    private final UserService userService;
    private final BlackListTokens blackListTokens;

    public RegisterService(JwtService jwtService, UserService userService, BlackListTokens blackListTokens) {
        this.blackListTokens = blackListTokens;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public AuthResponse registration(RegDto regDto) {
        User user = userService.registration(regDto);

        blackListTokens.initIfAbsent(user.getId().toString());

        String accessToken = jwtService.generateAccessToken(user.getId());
        String refreshToken = jwtService.generateRefreshToken(user.getId());

        return new AuthResponse(accessToken, refreshToken);
    }
}