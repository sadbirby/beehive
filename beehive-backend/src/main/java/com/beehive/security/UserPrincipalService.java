package com.beehive.security;

import java.util.Optional;

import com.beehive.entity.UsersEntity;
import com.beehive.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Primary
@Service
public class UserPrincipalService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        Optional<UsersEntity> user = usersRepository.findByLoginId(loginId);
        return user.map(UserPrincipal::new)
                   .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + loginId));
    }
}
