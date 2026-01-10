package com.beehive.authservice.client;

import com.commons.response.constant.RegisterUserConflict;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "${beehive.auth-service-url}")
public interface UserServiceClient {
    @PostMapping("/api/v1/users/internal/exists-email-username")
    RegisterUserConflict userExistsInternal(
            @RequestParam("email") String email,
            @RequestParam("username") String username
    );
}
