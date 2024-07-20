package com.beehive.repository;

import com.beehive.entity.PostEntity;
import com.beehive.entity.projection.PostEntityProjection;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

  @Query(
      "SELECT p.postId AS postId, p.postTitle AS postTitle, p.postBody AS postBody, p.postedBy AS postedBy, p.postedOn AS postedOn, p.numberOfLikes AS numberOfLikes, p.numberOfReplies AS numberOfReplies FROM PostEntity p WHERE p.postId = :postId")
  Optional<PostEntityProjection> findPostByPostId(@Param("postId") Long postId);

  @Query(
      "SELECT p.postId AS postId, p.postTitle AS postTitle, p.postBody AS postBody, p.postedBy AS postedBy, p.postedOn AS postedOn, p.numberOfLikes AS numberOfLikes, p.numberOfReplies AS numberOfReplies FROM PostEntity p")
  Page<PostEntityProjection> findAllPosts(Pageable pageable);

  @Query(
      "SELECT p.postId AS postId, p.postTitle AS postTitle, p.postBody AS postBody, p.postedBy AS postedBy, p.postedOn AS postedOn, p.numberOfLikes AS numberOfLikes, p.numberOfReplies AS numberOfReplies FROM PostEntity p WHERE p.postedBy = :postedBy")
  Page<PostEntityProjection> findAllPostsByPostedBy(
      @Param("postedBy") String postedBy, Pageable pageable);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Transactional
  @Query("UPDATE PostEntity p SET p.numberOfLikes = :numberOfLikes WHERE p.postId = :postId")
  void updateNumberOfLikesByPostId(
      @Param("postId") Long postId, @Param("numberOfLikes") Long numberOfLikes);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Transactional
  @Query("UPDATE PostEntity p SET p.numberOfReplies = :numberOfReplies WHERE p.postId = :postId")
  void updateNumberOfRepliesByPostId(
      @Param("postId") Long postId, @Param("numberOfReplies") Long numberOfLikes);
}
