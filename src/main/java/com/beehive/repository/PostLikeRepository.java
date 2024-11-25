package com.beehive.repository;

import com.beehive.entity.PostLikeEntity;
import com.beehive.entity.composite.PostLikeId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * For 'likes' on posts.
 */
@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, PostLikeId> {

    @Query(
            value =
                    """
                            SELECT COUNT(l) FROM PostLikeEntity l WHERE l.postId = :postId""")
    Long countByPostId(@Param("postId") Long postId);

    @Query(
            value =
                    """
                            SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM PostLikeEntity l
                            WHERE l.postId = :postId AND l.postLikedBy = :postLikedBy""")
    Boolean existsByPostIdAndPostLikedBy(
            @Param("postId") Long postId, @Param("postLikedBy") String postLikedBy);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(
            value =
                    """
                            INSERT INTO PostLikeEntity l (l.postId, l.postLikedBy) VALUES (:postId, :postLikedBy)""")
    int saveEntity(@Param("postId") Long postId, @Param("postLikedBy") String postLikedBy);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(
            value =
                    """
                            DELETE FROM PostLikeEntity l WHERE l.postId = :postId AND l.postLikedBy = :postLikedBy""")
    int deleteByPostIdAndPostLikedBy(
            @Param("postId") Long postId, @Param("postLikedBy") String postLikedBy);
}
