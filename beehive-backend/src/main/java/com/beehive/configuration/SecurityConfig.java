package com.beehive.configuration;

import com.beehive.repository.UserRepository;
import com.beehive.security.JwtFilter;
import com.beehive.security.UserPrincipalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
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
            // other public endpoints of your API may be appended to this array
            "/actuator/**"
    };
    private final JwtFilter jwtFilter;
    private final UserRepository userRepository;

    public SecurityConfig(JwtFilter jwtFilter, UserRepository userRepository) {
        this.jwtFilter = jwtFilter;
        this.userRepository = userRepository;
    }

    // Defines a UserDetailsService bean for user authentication
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserPrincipalService(userRepository);
    }

    // Configures the security filter chain
    @Bean
    @Profile("!test")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Disable CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Disable CSRF protection
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Permit all requests to certain URLs
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        // Require authentication for all other requests
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        // Set session management to stateless
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Register the authentication provider
                .authenticationProvider(authenticationProvider())
                // Add the JWT filter before processing the request
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Profile("test")
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(CorsConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    // Creates a DaoAuthenticationProvider to handle user authentication
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // Defines a PasswordEncoder bean that uses bcrypt hashing by default for password encoding
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Defines an AuthenticationManager bean to manage authentication processes
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // CORS configuration to allow all sources
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
