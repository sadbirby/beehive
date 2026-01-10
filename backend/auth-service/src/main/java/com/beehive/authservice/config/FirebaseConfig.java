package com.beehive.authservice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {
    private final String credentials;

    public FirebaseConfig(@Value("${firebase.credentials}") String credentials) {
        this.credentials = credentials;
    }

    @Bean
    FirebaseAuth initializeFirebaseAuth() throws IOException {
        var serviceAccount = new ByteArrayInputStream(credentials.getBytes(StandardCharsets.UTF_8));
        var options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
        var firebaseApp = FirebaseApp.initializeApp(options);
        return FirebaseAuth.getInstance(firebaseApp);
    }
}
