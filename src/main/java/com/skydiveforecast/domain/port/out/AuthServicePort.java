package com.skydiveforecast.domain.port.out;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface AuthServicePort {

    String extractUsername(String token);

    boolean validateToken(String token, UserDetails userDetails);

    Long getCurrentUserId();

    boolean isTokenValid(String token);

    Long extractUserId(String token);

    Set<String> extractRoles(String token);

    Set<String> extractPermissions(String token);
}
