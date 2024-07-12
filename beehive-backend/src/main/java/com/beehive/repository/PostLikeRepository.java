package com.beehive.repository;

import com.beehive.composite.PostLikeId;
import com.beehive.entity.PostLikeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, PostLikeId> {

  @Query("SELECT COUNT(l) FROM PostLikeEntity l WHERE l.postId = :postId")
  Long countByPostId(@Param("postId") Long postId);

  @Query(
      "SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM PostLikeEntity l WHERE l.postId = :postId AND l.postLikedBy = :postLikedBy")
  Boolean existsByPostIdAndPostLikedBy(
      @Param("postId") Long postId, @Param("postLikedBy") String postLikedBy);

  @Modifying
  @Query("DELETE FROM PostLikeEntity l WHERE l.postId = :postId AND l.postLikedBy = :postLikedBy")
  void deleteByPostIdAndPostLikedBy(
      @Param("postId") Long postId, @Param("postLikedBy") String postLikedBy);
}
