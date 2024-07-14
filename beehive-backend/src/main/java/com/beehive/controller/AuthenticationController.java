package com.beehive.controller;

import com.beehive.request.AuthenticationRequest;
import com.beehive.response.AuthenticationResponse;
import com.beehive.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1.0/login")
public class AuthenticationController {

  private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthenticationController(
      AuthenticationManager authenticationManager, JwtService jwtService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @PostMapping(path = "/authenticate")
  public AuthenticationResponse authenticate(
      @RequestBody AuthenticationRequest authenticationRequest) {

    // Authenticate the user
    Authentication authenticate =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));
    logger.info("Authentication controller: Logged in");

    // If authentication is successful, generate and return a JWT token
    if (authenticate.isAuthenticated()) {
      return new AuthenticationResponse(
          jwtService.generateToken(authenticationRequest.getUsername()), "SUCCESS");
    } else {
      throw new UsernameNotFoundException("USER NOT FOUND: " + authenticationRequest.getUsername());
    }
  }
}
