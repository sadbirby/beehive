package com.beehive.configuration;

import com.beehive.repository.UserRepository;
import com.beehive.security.JwtFilter;
import com.beehive.security.UserPrincipalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .cors(Customizer.withDefaults()) // Apply CORS
        .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(AUTH_WHITELIST)
                    .permitAll() // Permit all requests to certain URLs
                    .anyRequest()
                    .authenticated()) // Require authentication for all other requests
        .sessionManagement(
            session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS)) // Set session management to stateless
        .authenticationProvider(authenticationProvider()) // Register the authentication provider
        .addFilterBefore(
            jwtFilter,
            UsernamePasswordAuthenticationFilter
                .class) // Add the JWT filter before processing the request
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
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
