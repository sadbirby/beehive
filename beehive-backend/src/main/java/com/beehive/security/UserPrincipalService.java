package com.beehive.security;

import com.beehive.entity.UserEntity;
import com.beehive.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@Service
public class UserPrincipalService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserPrincipalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> user = userRepository.findByUsername(username);
        return user.map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));
    }
}
