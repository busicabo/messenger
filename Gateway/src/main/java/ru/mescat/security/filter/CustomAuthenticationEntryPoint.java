package ru.mescat.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.mescat.security.util.AuthCookieService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AuthCookieService authCookieService;

    public CustomAuthenticationEntryPoint(AuthCookieService authCookieService) {
        this.authCookieService = authCookieService;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        if (isBrowserPageRequest(request)) {
            String refreshToken = authCookieService.extractCookieValue(request, "refresh_token");

            if (refreshToken != null && !refreshToken.isBlank()) {
                String redirectTo = buildInternalRedirectTarget(request);
                String encodedRedirect = URLEncoder.encode(redirectTo, StandardCharsets.UTF_8);
                response.sendRedirect("/auth/refresh?redirect=" + encodedRedirect);
                return;
            }

            response.sendRedirect("/auth/login");
            return;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write("Требуется авторизация");
    }

    private boolean isBrowserPageRequest(HttpServletRequest request) {
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        return accept != null && accept.contains(MediaType.TEXT_HTML_VALUE);
    }

    private String buildInternalRedirectTarget(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String query = request.getQueryString();

        if (query == null || query.isBlank()) {
            return requestUri;
        }

        return requestUri + "?" + query;
    }
}
