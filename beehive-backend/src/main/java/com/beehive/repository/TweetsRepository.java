package com.beehive.repository;

import java.util.Date;
import java.util.List;

import com.beehive.entity.TweetsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetsRepository extends JpaRepository<TweetsEntity, String> {

    List<TweetsEntity> findByUserTweetId(String userId);

    @Query("SELECT t FROM TweetsEntity t WHERE t.dateOfPost BETWEEN :startDate AND :endDate ORDER BY t.likes DESC LIMIT 5")
    List<TweetsEntity> findPopularTweets(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
