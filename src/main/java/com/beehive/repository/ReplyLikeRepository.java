package com.beehive.repository;

import com.beehive.entity.ReplyLikeEntity;
import com.beehive.entity.composite.ReplyLikeId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyLikeRepository extends JpaRepository<ReplyLikeEntity, ReplyLikeId> {

    @Query("SELECT COUNT(r) FROM ReplyLikeEntity r WHERE r.replyId = :replyId")
    Long countByReplyId(@Param("replyId") Long replyId);

    @Query(
            """
                    SELECT CASE WHEN COUNT(r) > 0
                    THEN true ELSE false END
                    FROM ReplyLikeEntity r WHERE r.replyId = :replyId AND r.replyLikedBy = :replyLikedBy
                    """)
    Boolean existsByReplyIdAndReplyLikedBy(
            @Param("replyId") Long replyId, @Param("replyLikedBy") String replyLikedBy);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(
            "DELETE FROM ReplyLikeEntity r WHERE r.replyId = :replyId AND r.replyLikedBy = :replyLikedBy")
    void deleteByReplyIdAndReplyLikedBy(
            @Param("replyId") Long replyId, @Param("replyLikedBy") String replyLikedBy);
}
