package ru.mescat.security.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.mescat.security.dto.AuthResponse;
import ru.mescat.security.dto.LoginDto;

import java.util.UUID;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BlackListTokens blackListTokens;

    public LoginService(AuthenticationManager authenticationManager, JwtService jwtService, BlackListTokens blackListTokens) {
        this.blackListTokens = blackListTokens;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UUID userId = UUID.fromString(userDetails.getUsername());

        blackListTokens.initIfAbsent(userId.toString());

        String accessToken = jwtService.generateAccessToken(userId);
        String refreshToken = jwtService.generateRefreshToken(userId);

        return new AuthResponse(accessToken, refreshToken);
    }
}