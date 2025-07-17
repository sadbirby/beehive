package com.beehive.domain.service;

public interface PostLikeService {

    Long servicePostLikeGetCountByPostId(Long postId);

    Boolean servicePostLikeCheckIfUserExists(Long postId, String postLikedBy);

    Boolean servicePostLikeAddUpvote(Long postId, String username);

    Boolean servicePostLikeRevertUpvote(Long postId, String username);
}
