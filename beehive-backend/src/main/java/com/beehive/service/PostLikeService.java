package com.beehive.service;

public interface PostLikeService {

  Long servicePostLikeGetCountByPostId(Long postId);

  Boolean servicePostLikeCheckIfUserExists(Long postId, String likedBy);

  Boolean servicePostLikeAddUpvote(Long postId, String username);

  Boolean servicePostRevertUpvote(Long postId, String username);
}
