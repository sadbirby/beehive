package com.beehive.controller;

import com.beehive.request.AuthRequest;
import com.beehive.response.AuthResponse;
import com.beehive.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1.0/login")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private JwtService jwtService;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping(path = "/authenticate")
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {

        // Authenticate the user
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLoginId(), authRequest.getPassword()));
        logger.info("Authentication controller: " + "trying to log in [" + authenticate.isAuthenticated() + "]");

        // If authentication is successful, generate and return a JWT token
        if (authenticate.isAuthenticated()) {
            return new AuthResponse(jwtService.generateToken(authRequest.getLoginId()), "SUCCESS");
        } else {
            throw new UsernameNotFoundException("USER NOT FOUND: " + authRequest.getLoginId());
        }
    }
}
