package com.beehive.repository;

import java.util.List;

import com.beehive.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    List<ReplyEntity> findByPost_PostId(Long postId);

    @Query("SELECT COUNT(r) FROM ReplyEntity r WHERE r.post.postId = :postId")
    Long countNumberOfRepliesByPostId(@Param("postId") Long postId);
}
