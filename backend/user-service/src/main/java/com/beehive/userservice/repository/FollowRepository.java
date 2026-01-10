package com.beehive.userservice.repository;

import com.beehive.userservice.model.entity.Follow;
import com.beehive.userservice.model.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {
}
