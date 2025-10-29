package com.skydiveforecast.infrastructure.config;

import com.skydiveforecast.infrastructure.security.AuthService;
import com.skydiveforecast.infrastructure.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void jwtAuthenticationFilter_ShouldCreateFilter() {
        // Arrange & Act
        JwtAuthenticationFilter filter = securityConfig.jwtAuthenticationFilter();

        // Assert
        assertThat(filter).isNotNull();
    }

    @Test
    void passwordEncoder_ShouldReturnBCryptPasswordEncoder() {
        // Arrange & Act
        PasswordEncoder encoder = securityConfig.passwordEncoder();

        // Assert
        assertThat(encoder).isNotNull();
        assertThat(encoder).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    void authenticationProvider_ShouldReturnDaoAuthenticationProvider() {
        // Arrange & Act
        AuthenticationProvider provider = securityConfig.authenticationProvider();

        // Assert
        assertThat(provider).isNotNull();
        assertThat(provider).isInstanceOf(DaoAuthenticationProvider.class);
    }

    @Test
    void authenticationManager_ShouldReturnAuthenticationManager() throws Exception {
        // Arrange
        AuthenticationConfiguration config = mock(AuthenticationConfiguration.class);
        AuthenticationManager expectedManager = mock(AuthenticationManager.class);
        when(config.getAuthenticationManager()).thenReturn(expectedManager);

        // Act
        AuthenticationManager manager = securityConfig.authenticationManager(config);

        // Assert
        assertThat(manager).isNotNull();
        assertThat(manager).isEqualTo(expectedManager);
    }

    @Test
    void corsConfigurationSource_ShouldCreateCorsConfiguration() {
        // Arrange & Act
        CorsConfigurationSource source = securityConfig.corsConfigurationSource();

        // Assert
        assertThat(source).isNotNull();
    }
}
