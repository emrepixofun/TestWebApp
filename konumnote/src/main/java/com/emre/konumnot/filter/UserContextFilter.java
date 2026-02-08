package com.emre.konumnot.filter;

import com.emre.konumnot.context.UserContext;
import com.emre.konumnot.model.User;
import com.emre.konumnot.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Her istekte X-User-UUID header'ını okur, kullanıcıyı getirir/oluşturur ve UserContext'e set eder.
 */
@Component("konumnoteUserContextFilter")
@Order(2)
@RequiredArgsConstructor
public class UserContextFilter extends OncePerRequestFilter {

    public static final String USER_UUID_HEADER = "X-User-UUID";

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!request.getRequestURI().startsWith("/konumnot")) {
                filterChain.doFilter(request, response);
                return;
            }
            String uuid = request.getHeader(USER_UUID_HEADER);
            if (uuid == null || uuid.isBlank()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"error\":\"X-User-UUID header gerekli\"}");
                return;
            }
            uuid = uuid.trim();
            User user = userService.getOrCreateByUuid(UUID.fromString(uuid));
            UserContext.setUser(user);
            filterChain.doFilter(request, response);
        } finally {
            UserContext.clear();
        }
    }
}
