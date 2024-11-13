package com.test.TUdipsaiApi.seguridad;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "access-token-udipsai";
    private static final String TOKEN = "nOsr9FprhOWfLEti5KGJUK6RJWL8R0Ek";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        System.out.println("JwtAuthenticationFilter is processing the request.");

        String token = request.getHeader(AUTH_HEADER);

        if (token == null || !TOKEN.equals(token)) {
            System.out.println("Invalid or missing token.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Token inválido, rechazar
            return;
        }

        System.out.println("Token is valid.");

        // Crear una autenticación forzada con un usuario "dummy" y rol "USER"
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("user", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        // Establecer la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }
}
