package com.beehive.repository;

import java.util.List;

import com.beehive.entity.TweetsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetsRepository extends JpaRepository<TweetsEntity, String> {

    List<TweetsEntity> findByUserTweetId(String userId);
}
