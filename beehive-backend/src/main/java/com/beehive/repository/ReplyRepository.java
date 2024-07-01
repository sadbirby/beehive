package com.beehive.repository;

import com.beehive.dto.ReplyDto;
import com.beehive.entity.ReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

//    @Query("SELECT r.replyId, r.post.postId, r.replyBody, r.repliedBy, r.repliedOn FROM ReplyEntity r WHERE r.post.postId = :postId")
    Page<ReplyEntity> findByPost_PostId(@Param("postId") Long postId, Pageable pageable);

    @Query("SELECT COUNT(r) FROM ReplyEntity r WHERE r.post.postId = :postId")
    Long countNumberOfRepliesByPostId(@Param("postId") Long postId);
}
