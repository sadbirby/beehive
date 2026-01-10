package com.beehive.authservice.controller;

import com.beehive.authservice.model.request.CreateUserRequest;
import com.beehive.authservice.model.response.CreateUserResponse;
import com.beehive.authservice.service.AuthService;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<CreateUserResponse> createUser(
            @RequestBody @Valid CreateUserRequest request
    ) throws FirebaseAuthException {
        CreateUserResponse body = service.createFirebaseUser(request);
        URI location = URI.create("/api/v1/users/" + body.id());
        return ResponseEntity.created(location).body(body);
    }
}
