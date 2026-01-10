package com.commons.security.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

@Configuration
public class FeignTokenRelayConfig {
    private final String expectedAudience;

    public FeignTokenRelayConfig(@Value("${firebase.token.audience}") String expectedAudience) {
        this.expectedAudience = expectedAudience;
    }

    @Bean
    public RequestInterceptor relayUserJwt(@Lazy @AuthenticationPrincipal Jwt jwt) {
        return (RequestTemplate tpl) -> {
            final var aud = jwt.getClaims().get("aud");
            if ((aud instanceof List) && ((List<?>) aud).contains(expectedAudience)) {
                tpl.header("Authorization", "Bearer " + jwt.getTokenValue());
            }
        };
    }
}
