package com.beehive.repository;

import java.util.Date;
import java.util.List;

import com.beehive.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findByPostedBy(String postedBy);

/*    @Query("SELECT p FROM PostEntity p WHERE [.postedOn BETWEEN :startDate AND :endDate ORDER BY p.numberOfLikes DESC LIMIT 5")
    List<PostEntity> findPopularTweets(@Param("startDate") Date startDate, @Param("endDate") Date endDate);*/
}
