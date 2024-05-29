package com.beehive.controller;

import com.beehive.request.AuthRequest;
import com.beehive.security.JwtService;
import com.beehive.security.UserPrincipalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping(value = "/api/v1.0/user")
public class AuthenticationController {

//    @Autowired private UserPrincipalService userPrincipalService;

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private JwtService jwtService;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping(path = "/authenticate")
    public String authenticate(@RequestBody AuthRequest authRequest) {

        System.out.println("\n\n\n*******************************************\n\n-->1------\n\n\n*******************************************\n\n");
        // Authenticate the user
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLoginId(), authRequest.getPassword()));

        System.out.println("\n\n\n*******************************************\n\n-->2------\n\n\n*******************************************\n\n");

        // If authentication is successful, generate and return a JWT token
        if (authenticate.isAuthenticated()) {
            System.out.println("\n\n\n*******************************************\n\n-->3------\n\n\n*******************************************\n\n");

            return jwtService.generateToken(authRequest.getLoginId());
        } else {
            throw new UsernameNotFoundException("USER NOT FOUND: " + authRequest.getLoginId());
        }
    }
}
