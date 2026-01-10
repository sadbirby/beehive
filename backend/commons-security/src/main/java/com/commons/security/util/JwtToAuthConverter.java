package com.commons.security.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtToAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        var scopes = new LinkedHashSet<String>();
        var authorities = new ArrayList<GrantedAuthority>();
        var roles = jwt.getClaims().get("role");

        if (roles instanceof Collection<?>) {
            for (Object r : (Collection<?>) roles) {
                scopes.add(String.valueOf(r));
            }
        }

        for (final String s : scopes) {
            authorities.add(() -> "SCOPE_" + s);
        }

        return new JwtAuthenticationToken(jwt, authorities);
    }
}