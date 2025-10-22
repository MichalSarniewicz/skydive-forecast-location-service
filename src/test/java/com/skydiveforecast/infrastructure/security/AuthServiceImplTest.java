package com.skydiveforecast.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    private String secretKey;
    private Key signingKey;

    @BeforeEach
    void setUp() {
        secretKey = "mySecretKeyForTestingPurposesOnlyAndItNeedsToBeVeryLongForHS256Algorithm";
        ReflectionTestUtils.setField(authService, "secretKey", secretKey);
        signingKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Test
    void extractUsername_ShouldReturnUsername() {
        // Arrange
        String username = "test@example.com";
        String token = createToken(username, 1L, List.of(), List.of());

        // Act
        String extractedUsername = authService.extractUsername(token);

        // Assert
        assertThat(extractedUsername).isEqualTo(username);
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        // Arrange
        String username = "test@example.com";
        String token = createToken(username, 1L, List.of(), List.of());
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);

        // Act
        boolean isValid = authService.validateToken(token, userDetails);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    void validateToken_ShouldReturnFalseForDifferentUsername() {
        // Arrange
        String token = createToken("test@example.com", 1L, List.of(), List.of());
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("different@example.com");

        // Act
        boolean isValid = authService.validateToken(token, userDetails);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    void getCurrentUserId_ShouldReturnUserIdFromContext() {
        // Arrange
        CustomUserPrincipal principal = new CustomUserPrincipal(
                1L, "test@example.com", "password", true, List.of());
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.setContext(securityContext);

        // Act
        Long userId = authService.getCurrentUserId();

        // Assert
        assertThat(userId).isEqualTo(1L);
        
        SecurityContextHolder.clearContext();
    }

    @Test
    void getCurrentUserId_ShouldReturnNullWhenNoAuthentication() {
        // Arrange
        SecurityContextHolder.clearContext();

        // Act
        Long userId = authService.getCurrentUserId();

        // Assert
        assertThat(userId).isNull();
    }

    @Test
    void isTokenValid_ShouldReturnTrueForValidToken() {
        // Arrange
        String token = createToken("test@example.com", 1L, List.of(), List.of());

        // Act
        boolean isValid = authService.isTokenValid(token);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    void isTokenValid_ShouldReturnFalseForExpiredToken() {
        // Arrange
        String token = createExpiredToken("test@example.com", 1L);

        // Act
        boolean isValid = authService.isTokenValid(token);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    void extractUserId_ShouldReturnUserId() {
        // Arrange
        Long userId = 123L;
        String token = createToken("test@example.com", userId, List.of(), List.of());

        // Act
        Long extractedUserId = authService.extractUserId(token);

        // Assert
        assertThat(extractedUserId).isEqualTo(userId);
    }

    @Test
    void extractRoles_ShouldReturnRoles() {
        // Arrange
        List<String> roles = List.of("ADMIN", "USER");
        String token = createToken("test@example.com", 1L, roles, List.of());

        // Act
        Set<String> extractedRoles = authService.extractRoles(token);

        // Assert
        assertThat(extractedRoles).containsExactlyInAnyOrder("ADMIN", "USER");
    }

    @Test
    void extractPermissions_ShouldReturnPermissions() {
        // Arrange
        List<String> permissions = List.of("READ", "WRITE");
        String token = createToken("test@example.com", 1L, List.of(), permissions);

        // Act
        Set<String> extractedPermissions = authService.extractPermissions(token);

        // Assert
        assertThat(extractedPermissions).containsExactlyInAnyOrder("READ", "WRITE");
    }

    private String createToken(String username, Long userId, List<String> roles, List<String> permissions) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("roles", roles)
                .claim("permissions", permissions)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(signingKey)
                .compact();
    }

    private String createExpiredToken(String username, Long userId) {
        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .issuedAt(new Date(System.currentTimeMillis() - 7200000))
                .expiration(new Date(System.currentTimeMillis() - 3600000))
                .signWith(signingKey)
                .compact();
    }
}
