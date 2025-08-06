package org.thej.foodorder.customers.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thej.foodorder.master.service.impl.JwtService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized");
            return;
        }

        String token = authHeader.substring(7);

        if (jwtService.isTokenExpired(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token expired");
            return;
        }

        String username = jwtService.extractUsername(token);

        Object roles = jwtService.extractRoles(token);
        List<SimpleGrantedAuthority> authorities = Collections.emptyList();

        if (roles instanceof List<?>) {
            authorities = ((List<?>) roles).stream()
                    .filter(r -> r instanceof Map)
                    .map(r -> ((Map<?, ?>) r).get("roleName"))
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .map(rn -> rn.startsWith("ROLE_") ? rn : "ROLE_" + rn)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                new User(username, "", authorities), null, authorities);

        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);

        System.out.println("Authorities for user " + username + ":");
        auth.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));

        filterChain.doFilter(request, response);
    }
}
