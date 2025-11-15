package com.skydiveforecast.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private AuthService authService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(authService, userDetailsService);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_ShouldAuthenticateWithValidToken() throws ServletException, IOException {
        // Arrange
        String token = "valid.jwt.token";
        String username = "test@example.com";
        CustomUserPrincipal userDetails = new CustomUserPrincipal(
                1L, username, "password", true, List.of());

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(authService.extractUsername(token)).thenReturn(username);
        when(authService.isTokenValid(token)).thenReturn(true);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(authService.validateToken(token, userDetails)).thenReturn(true);
        when(authService.extractRoles(token)).thenReturn(Set.of("USER"));
        when(authService.extractPermissions(token)).thenReturn(Set.of("READ"));

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ShouldNotAuthenticateWithoutAuthorizationHeader() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ShouldNotAuthenticateWithInvalidToken() throws ServletException, IOException {
        // Arrange
        String token = "invalid.jwt.token";
        String username = "test@example.com";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(authService.extractUsername(token)).thenReturn(username);
        when(authService.isTokenValid(token)).thenReturn(false);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ShouldClearContextOnException() throws ServletException, IOException {
        // Arrange
        String token = "malformed.token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(authService.extractUsername(token)).thenThrow(new RuntimeException("Invalid token"));

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ShouldNotAuthenticateWhenTokenValidationFails() throws ServletException, IOException {
        // Arrange
        String token = "valid.jwt.token";
        String username = "test@example.com";
        CustomUserPrincipal userDetails = new CustomUserPrincipal(
                1L, username, "password", true, List.of());

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(authService.extractUsername(token)).thenReturn(username);
        when(authService.isTokenValid(token)).thenReturn(true);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(authService.validateToken(token, userDetails)).thenReturn(false);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }
}
