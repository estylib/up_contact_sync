package com.up.up_contact_sync.service.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public String getUsernameFromToken(Authentication authentication) {
        final Jwt principal = (Jwt) authentication.getPrincipal();
        return principal.getClaim("preferred_username");
    }
}
