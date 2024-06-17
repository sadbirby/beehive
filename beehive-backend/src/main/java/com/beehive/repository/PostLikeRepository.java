package com.beehive.repository;

import com.beehive.composite.PostLikeId;
import com.beehive.entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, PostLikeId> {

    @Query("SELECT COUNT(l) FROM PostLikeEntity l WHERE l.postId = :postId")
    Long countByPostId(@Param("postId") Long postId);

    Boolean existsByPostIdAndPostLikedBy(Long postId, String postLikedBy);
}
