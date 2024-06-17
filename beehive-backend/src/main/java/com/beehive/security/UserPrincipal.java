package com.beehive.security;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.beehive.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    String username = null;
    String password = null;
    Set<SimpleGrantedAuthority> authorities;

    public UserPrincipal(UserEntity userEntity) {
        username = userEntity.getUsername();
        password = userEntity.getPassword();
        authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
