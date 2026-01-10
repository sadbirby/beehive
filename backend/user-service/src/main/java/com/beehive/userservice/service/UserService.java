package com.beehive.userservice.service;

import com.beehive.userservice.model.constant.AccountStatus;
import com.beehive.userservice.model.constant.ProfileCategory;
import com.beehive.userservice.model.entity.Follow;
import com.beehive.userservice.model.entity.FollowId;
import com.beehive.userservice.model.entity.User;
import com.beehive.userservice.model.event.CreateUserEvent;
import com.beehive.userservice.model.mapper.UserMapper;
import com.beehive.userservice.repository.UserRepository;
import com.beehive.userservice.util.FingerPrint;
import com.commons.response.constant.RegisterUserConflict;
import com.commons.response.exception.ConflictException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final FollowService followService;
    private final KafkaProducerService producer;

    @Transactional
    public void create(CreateUserEvent event) {
        try {
            String username = event.username();
            String email = event.email();
            String userHash = FingerPrint.computeUserHash(event.uid(), event.username(), event.email());

            Optional<User> byUsername = repository.findByUsername(username);
            if (byUsername.isPresent()) {
                throw new ConflictException("Same username used with different data");
            }

            Optional<User> byEmail = repository.findByEmail(email);
            if (byEmail.isPresent()) {
                throw new ConflictException("Same emailId used with different data");
            }

            User entity = UserMapper.toUserEntityFromEvent(event);
            entity.setPreComputedHash(userHash);
            entity.setAccountStatus(AccountStatus.ACCOUNT_ACTIVE);
            entity.setProfileCategory(ProfileCategory.PROFILE_REGULAR);

            User saved = repository.saveAndFlush(entity);
            log.info("Created User: {}", saved);
        } catch (RuntimeException e) {
            producer.produceUserRegistrationFailedEvent(event.uid());
        }
    }

    @Transactional
    public boolean follow(String followerId, String followeeId) {
        if (userExistsById(followerId) && userExistsById(followeeId)) {
            User follower = repository.getReferenceById(followerId);
            User following = repository.getReferenceById(followeeId);

            Follow entity = new Follow();
            entity.setId(new FollowId(followerId, followeeId));
            entity.setFollowerId(follower);
            entity.setFolloweeId(following);

            return followService.follow(entity);
        }

        return Boolean.FALSE;
    }

    @Transactional
    public boolean unfollow(String followerId, String followeeId) {
        if (userExistsById(followerId) && userExistsById(followeeId)) {
            return followService.unfollow(new FollowId(followerId, followeeId));
        }

        return Boolean.FALSE;
    }

    @Transactional(readOnly = true)
    public boolean userExistsById(String id) {
        return repository.existsById(id);
    }

    @Transactional(readOnly = true)
    public RegisterUserConflict userExistsByEmailUsername(String email, String username) {
        boolean byEmail = repository.existsByEmail(email);
        boolean byUsername = repository.existsByUsername(username);

        if (byEmail && byUsername) {
            return RegisterUserConflict.EMAIL_USERNAME;
        } else if (byEmail) {
            return RegisterUserConflict.EMAIL;
        } else if (byUsername) {
            return RegisterUserConflict.USERNAME;
        }
        return RegisterUserConflict.NONE;
    }
}
