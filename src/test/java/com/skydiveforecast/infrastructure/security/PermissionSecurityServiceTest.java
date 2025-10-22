package com.skydiveforecast.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PermissionSecurityServiceTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private PermissionSecurityService permissionSecurityService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void hasPermission_ShouldReturnTrueWhenUserHasPermissionInContext() {
        // Arrange
        String permission = "READ";
        CustomUserPrincipal principal = new CustomUserPrincipal(
                1L, "test@example.com", "password", true, 
                Set.of(), Set.of(), Set.of(permission));
        
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean hasPermission = permissionSecurityService.hasPermission(permission);

        // Assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void hasPermission_ShouldReturnFalseWhenUserDoesNotHavePermission() {
        // Arrange
        String permission = "WRITE";
        CustomUserPrincipal principal = new CustomUserPrincipal(
                1L, "test@example.com", "password", true, 
                Set.of(), Set.of(), Set.of("READ"));
        
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean hasPermission = permissionSecurityService.hasPermission(permission);

        // Assert
        assertThat(hasPermission).isFalse();
    }

    @Test
    void hasPermission_ShouldReturnFalseWhenNoAuthentication() {
        // Arrange
        SecurityContextHolder.clearContext();

        // Act
        boolean hasPermission = permissionSecurityService.hasPermission("READ");

        // Assert
        assertThat(hasPermission).isFalse();
    }

    @Test
    void hasPermission_ShouldExtractFromTokenWhenPermissionsNotInContext() {
        // Arrange
        String permission = "READ";
        String token = "valid.jwt.token";
        
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("username");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(authService.isTokenValid(token)).thenReturn(true);
        when(authService.extractPermissions(token)).thenReturn(Set.of(permission));
        
        SecurityContextHolder.setContext(securityContext);
        RequestContextHolder.setRequestAttributes(attributes);

        // Act
        boolean hasPermission = permissionSecurityService.hasPermission(permission);

        // Assert
        assertThat(hasPermission).isTrue();
    }

    @Test
    void hasPermission_ShouldReturnFalseWhenTokenIsInvalid() {
        // Arrange
        String token = "invalid.jwt.token";
        
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("username");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(authService.isTokenValid(token)).thenReturn(false);
        
        SecurityContextHolder.setContext(securityContext);
        RequestContextHolder.setRequestAttributes(attributes);

        // Act
        boolean hasPermission = permissionSecurityService.hasPermission("READ");

        // Assert
        assertThat(hasPermission).isFalse();
    }
}
