package com.beehive.repository;

import com.beehive.entity.ReplyEntity;
import com.beehive.entity.projection.ReplyEntityProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    @Query(
            """
                    SELECT r.replyId AS replyId, r.replyBody AS replyBody,
                    r.repliedBy AS repliedBy, r.repliedOn AS repliedOn,
                    r.numberOfLikes AS numberOfLikes FROM ReplyEntity r WHERE r.replyId = :replyId
                    """)
    Optional<ReplyEntityProjection> findReplyByReplyId(@Param("replyId") Long replyId);

    @Query(
            """
                    SELECT r.replyId AS replyId, r.replyBody AS replyBody,
                    r.repliedBy AS repliedBy, r.repliedOn AS repliedOn,
                    r.numberOfLikes AS numberOfLikes FROM ReplyEntity r WHERE r.post.postId = :postId
                    """)
    Page<ReplyEntityProjection> findAllRepliesByPostId(
            @Param("postId") Long postId, Pageable pageable);

    @Query("SELECT COUNT(r) FROM ReplyEntity r WHERE r.post.postId = :postId")
    Long countNumberOfRepliesByPostId(@Param("postId") Long postId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE ReplyEntity r SET r.numberOfLikes = :numberOfLikes WHERE r.replyId = :replyId")
    void updateNumberOfLikesByReplyId(
            @Param("replyId") Long replyId, @Param("numberOfLikes") Long numberOfLikes);
}
