package com.beehive.userservice.controller;

import com.beehive.userservice.service.UserService;
import com.commons.response.constant.RegisterUserConflict;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
class UserController {
    private final UserService service;

    /*@PostMapping
    ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse body = service.create(request);
        URI location = URI.create("/api/v1/users/" + body.username());
        return ResponseEntity.created(location).body(body);
    }*/

    @GetMapping("/internal/exists-email-username")
    RegisterUserConflict userExistsInternal(
            @RequestParam("email") String email,
            @RequestParam("username") String username
    ) {
        return service.userExistsByEmailUsername(email, username);
    }

    @PutMapping("{followerId}/follows/{followeeId}")
    @PreAuthorize("hasAuthority('SCOPE_user')")
    ResponseEntity<Void> followUser(
            @PathVariable String followerId,
            @PathVariable String followeeId
    ) {
        Boolean liked = service.follow(followerId, followeeId);
        if (liked) {
            URI location = URI.create("/api/v1/users/" + followerId + "/follows/" + followeeId);
            return ResponseEntity.created(location).body(null);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{followerId}/follows/{followeeId}")
    @PreAuthorize("hasAuthority('SCOPE_user')")
    ResponseEntity<Void> unfollowUser(
            @PathVariable String followerId,
            @PathVariable String followeeId
    ) {

        Boolean unfollowed = service.unfollow(followerId, followeeId);
        if (unfollowed) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    String authorities() {
        return Objects
                .requireNonNull(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getAuthorities())
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList().toString();
    }
}
