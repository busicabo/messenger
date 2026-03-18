package ru.mescat.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mescat.security.dto.AuthResponse;
import ru.mescat.security.dto.LoginDto;
import ru.mescat.security.dto.RegDto;
import ru.mescat.security.exception.RemoteServiceException;
import ru.mescat.security.service.LoginService;
import ru.mescat.security.service.RefreshService;
import ru.mescat.security.service.RegisterService;
import ru.mescat.security.util.AuthCookieService;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginService loginService;
    private final RegisterService registerService;
    private final RefreshService refreshService;
    private final AuthCookieService authCookieService;

    public AuthController(
            LoginService loginService,
            RegisterService registerService,
            RefreshService refreshService,
            AuthCookieService authCookieService
    ) {
        this.loginService = loginService;
        this.registerService = registerService;
        this.refreshService = refreshService;
        this.authCookieService = authCookieService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> verify(
            @RequestBody LoginDto loginDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            AuthResponse tokens = loginService.login(loginDto);
            authCookieService.addAuthCookies(response, request, tokens);
            return ResponseEntity.ok("Успешный вход");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Неверный логин или пароль");
        } catch (RemoteServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getResponseBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка сервера");
        }
    }

    @PostMapping("/reg")
    public ResponseEntity<String> registration(
            @RequestBody RegDto regDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            AuthResponse tokens = registerService.registration(regDto);
            authCookieService.addAuthCookies(response, request, tokens);
            return ResponseEntity.ok("Регистрация успешна");
        } catch (RemoteServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getResponseBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка сервера");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            String refreshToken = authCookieService.extractCookieValue(request, "refresh_token");
            AuthResponse tokens = refreshService.refresh(refreshToken);
            authCookieService.addAuthCookies(response, request, tokens);
            return ResponseEntity.ok("Токены обновлены");
        } catch (BadCredentialsException | IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (RemoteServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getResponseBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка сервера");
        }
    }

    @GetMapping("/refresh")
    public void refreshPageRequest(
            @RequestParam(value = "redirect", required = false, defaultValue = "/") String redirect,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        try {
            String refreshToken = authCookieService.extractCookieValue(request, "refresh_token");
            AuthResponse tokens = refreshService.refresh(refreshToken);
            authCookieService.addAuthCookies(response, request, tokens);
            response.sendRedirect(sanitizeRedirectTarget(redirect));
        } catch (Exception e) {
            authCookieService.clearAuthCookies(response, request);
            response.sendRedirect("/auth/login");
        }
    }

    private String sanitizeRedirectTarget(String redirect) {
        String decoded = URLDecoder.decode(redirect, StandardCharsets.UTF_8);

        if (decoded.isBlank()) {
            return "/";
        }

        if (!decoded.startsWith("/") || decoded.startsWith("//")) {
            return "/";
        }

        return decoded;
    }
}
