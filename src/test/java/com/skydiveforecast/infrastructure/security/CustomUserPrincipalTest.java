package com.skydiveforecast.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CustomUserPrincipalTest {

    @Test
    void constructor_ShouldCreatePrincipalWithBasicFields() {
        // Arrange
        Long userId = 1L;
        String username = "test@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        // Act
        CustomUserPrincipal principal = new CustomUserPrincipal(userId, username, password, true, authorities);

        // Assert
        assertThat(principal.getUserId()).isEqualTo(userId);
        assertThat(principal.getUsername()).isEqualTo(username);
        assertThat(principal.getPassword()).isEqualTo(password);
        assertThat(principal.isActive()).isTrue();
        assertThat(principal.getAuthorities()).isEqualTo(authorities);
        assertThat(principal.getRoles()).isEmpty();
        assertThat(principal.getPermissions()).isEmpty();
    }

    @Test
    void constructor_ShouldCreatePrincipalWithRolesAndPermissions() {
        // Arrange
        Long userId = 1L;
        String username = "test@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Set<String> roles = Set.of("ADMIN", "USER");
        Set<String> permissions = Set.of("READ", "WRITE");

        // Act
        CustomUserPrincipal principal = new CustomUserPrincipal(
                userId, username, password, true, authorities, roles, permissions);

        // Assert
        assertThat(principal.getUserId()).isEqualTo(userId);
        assertThat(principal.getUsername()).isEqualTo(username);
        assertThat(principal.getPassword()).isEqualTo(password);
        assertThat(principal.isActive()).isTrue();
        assertThat(principal.getAuthorities()).isEqualTo(authorities);
        assertThat(principal.getRoles()).containsExactlyInAnyOrder("ADMIN", "USER");
        assertThat(principal.getPermissions()).containsExactlyInAnyOrder("READ", "WRITE");
    }

    @Test
    void isAccountNonExpired_ShouldReturnTrue() {
        // Arrange
        CustomUserPrincipal principal = new CustomUserPrincipal(
                1L, "test@example.com", "password", true, List.of());

        // Act & Assert
        assertThat(principal.isAccountNonExpired()).isTrue();
    }

    @Test
    void isAccountNonLocked_ShouldReturnActiveStatus() {
        // Arrange
        CustomUserPrincipal activePrincipal = new CustomUserPrincipal(
                1L, "test@example.com", "password", true, List.of());
        CustomUserPrincipal inactivePrincipal = new CustomUserPrincipal(
                2L, "test2@example.com", "password", false, List.of());

        // Act & Assert
        assertThat(activePrincipal.isAccountNonLocked()).isTrue();
        assertThat(inactivePrincipal.isAccountNonLocked()).isFalse();
    }

    @Test
    void isCredentialsNonExpired_ShouldReturnTrue() {
        // Arrange
        CustomUserPrincipal principal = new CustomUserPrincipal(
                1L, "test@example.com", "password", true, List.of());

        // Act & Assert
        assertThat(principal.isCredentialsNonExpired()).isTrue();
    }

    @Test
    void isEnabled_ShouldReturnActiveStatus() {
        // Arrange
        CustomUserPrincipal activePrincipal = new CustomUserPrincipal(
                1L, "test@example.com", "password", true, List.of());
        CustomUserPrincipal inactivePrincipal = new CustomUserPrincipal(
                2L, "test2@example.com", "password", false, List.of());

        // Act & Assert
        assertThat(activePrincipal.isEnabled()).isTrue();
        assertThat(inactivePrincipal.isEnabled()).isFalse();
    }
}
