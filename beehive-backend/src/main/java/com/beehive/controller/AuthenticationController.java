package com.beehive.controller;

import com.beehive.request.AuthenticationRequest;
import com.beehive.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<String> authenticate(
      @RequestBody AuthenticationRequest authenticationRequest) {

    // Authenticate the user
    logger.info("Authentication Controller");
    Authentication authenticate =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

    // If authentication is successful, generate and return a JWT token
    if (authenticate.isAuthenticated()) {
      String token = jwtService.generateToken(authenticationRequest.getUsername());
      HttpCookie cookie =
          ResponseCookie.from("token", token).httpOnly(true).path("/").sameSite("Strict").build();
      return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("SUCCESS");
    } else {
      throw new UsernameNotFoundException("USER NOT FOUND: " + authenticationRequest.getUsername());
    }
  }
}
