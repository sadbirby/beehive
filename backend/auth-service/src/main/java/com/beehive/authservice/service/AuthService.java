package com.beehive.authservice.service;

import com.beehive.authservice.client.UserServiceClient;
import com.beehive.authservice.model.constant.Role;
import com.beehive.authservice.model.event.CreateUserEvent;
import com.beehive.authservice.model.request.CreateUserRequest;
import com.beehive.authservice.model.response.CreateUserResponse;
import com.commons.response.constant.RegisterUserConflict;
import com.commons.response.exception.ConflictException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private static final String ROLE = "role";

    private final FirebaseAuth firebaseAuth;
    private final KafkaProducerService producer;
    private final UserServiceClient client;

    public AuthService(
            FirebaseAuth firebaseAuth,
            KafkaProducerService producer,
            UserServiceClient client
    ) {
        this.firebaseAuth = firebaseAuth;
        this.producer = producer;
        this.client = client;
    }

    public CreateUserResponse createFirebaseUser(CreateUserRequest request) throws FirebaseAuthException {
        RegisterUserConflict result = client.userExistsInternal(request.email(), request.displayName());
        switch (result) {
            case NONE -> {
                var createUserRequest = new UserRecord.CreateRequest()
                        .setEmail(request.email())
                        .setPassword(request.password())
                        .setDisplayName(request.displayName());

                var user = firebaseAuth.createUser(createUserRequest);

                if (StringUtils.isBlank(user.getUid())) {
                    throw new IllegalStateException("Firebase user created but UID missing");
                }

                setFirebaseUserRole(user.getUid(), Role.USER);
                log.info("Firebase user created with UID {}", user.getUid());

                producer.produceUserIdentityCreatedEvent(new CreateUserEvent(
                        user.getUid(),
                        user.getEmail(),
                        user.getDisplayName(),
                        user.getPhotoUrl()
                ));

                return new CreateUserResponse(user.getUid(), user.getEmail());
            }
            case EMAIL_USERNAME -> throw new ConflictException("Email and Username already exists");
            case EMAIL -> throw new ConflictException("Email already exists");
            case USERNAME -> throw new ConflictException("Username already exists");
            default -> throw new IllegalStateException("Unexpected value: " + result);
        }
    }

    public void setFirebaseUserRole(String uid, Role role) throws FirebaseAuthException {
        var roles = switch (role) {
            case USER -> Set.of(Role.USER);
            case MODERATOR -> Set.of(Role.MODERATOR, Role.USER);
            case ADMIN -> Set.of(Role.ADMIN, Role.MODERATOR, Role.USER);
        };

        Map<String, Object> claims = Map.of(ROLE, roles);
        firebaseAuth.setCustomUserClaims(uid, claims);
    }

    public void deleteFirebaseUser(String uid) throws FirebaseAuthException {
        firebaseAuth.deleteUser(uid);
    }
}

