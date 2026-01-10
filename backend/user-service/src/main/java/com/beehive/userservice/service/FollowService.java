package com.beehive.userservice.service;

import com.beehive.userservice.model.entity.Follow;
import com.beehive.userservice.model.entity.FollowId;
import com.beehive.userservice.repository.FollowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class FollowService {
    private final FollowRepository repository;

    @Transactional
    public boolean follow(Follow entity) {
        if (!repository.existsById(entity.getId())) {
            repository.save(entity);
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Transactional
    public boolean unfollow(FollowId id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

}

