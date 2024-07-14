package com.beehive.repository;

import com.beehive.entity.PostEntity;
import com.beehive.entity.projection.PostEntityProjection;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<PostEntity, Long> {

  @Query(
      "SELECT  p.postId AS postId, p.postTitle AS postTitle, p.postBody AS postBody, p.postedBy AS postedBy, p.postedOn AS postedOn FROM PostEntity p WHERE p.postId = :postId")
  PostEntityProjection findPostById(@Param("postId") Long postId);

  @Query(
      "SELECT p.postId AS postId, p.postTitle AS postTitle, p.postBody AS postBody, p.postedBy AS postedBy, p.postedOn AS postedOn FROM PostEntity p")
  Page<PostEntityProjection> findAllPosts(Pageable pageable);

  @Query(
      "SELECT p.postId AS postId, p.postTitle AS postTitle, p.postBody AS postBody, p.postedBy AS postedBy, p.postedOn AS postedOn FROM PostEntity p WHERE p.postedBy = :postedBy")
  Page<PostEntityProjection> findAllPostsByPostedBy(
      @Param("postedBy") String postedBy, Pageable pageable);

  List<PostEntity> findByPostedBy(String postedBy);

  /*    @Query("SELECT p FROM PostEntity p WHERE p.postedOn BETWEEN :startDate AND :endDate ORDER BY p.numberOfLikes DESC LIMIT 5")
  List<PostEntity> findPopularTweets(@Param("startDate") Date startDate, @Param("endDate") Date endDate);*/
}
