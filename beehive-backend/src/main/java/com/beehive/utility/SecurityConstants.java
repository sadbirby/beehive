package com.beehive.utility;

public class SecurityConstants {
    public static final String[] AUTH_WHITELIST = {
            // -- Register & Authenticate User
            "/api/v1.0/login/authenticate",
            "/api/v1.0/user/forgot-password",
            "/api/v1.0/user/register",
            "/api/v1.0/user/check/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
    };
}
