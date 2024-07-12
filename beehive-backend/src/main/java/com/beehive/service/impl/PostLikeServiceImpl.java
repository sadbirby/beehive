package com.beehive.service.impl;

import com.beehive.entity.PostLikeEntity;
import com.beehive.repository.PostLikeRepository;
import com.beehive.service.PostLikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PostLikeServiceImpl implements PostLikeService {

  private final PostLikeRepository postLikeRepository;
  Logger logger = LoggerFactory.getLogger(PostLikeServiceImpl.class);

  public PostLikeServiceImpl(PostLikeRepository postLikeRepository) {
    this.postLikeRepository = postLikeRepository;
  }

  @Override
  public Long servicePostLikeGetCountByPostId(Long postId) {
    Long numberOfLikes = 0L;
    try {
      numberOfLikes = postLikeRepository.countByPostId(postId);
    } catch (Exception e) {
      logger.error("in servicePostLikeGetCountByPostId: ", e);
    }
    return numberOfLikes;
  }

  @Override
  public Boolean servicePostLikeCheckIfUserExists(Long postId, String likedBy) {
    Boolean hasUserLikedPost = false;
    try {
      hasUserLikedPost = postLikeRepository.existsByPostIdAndPostLikedBy(postId, likedBy);
    } catch (Exception e) {
      logger.error("in servicePostLikeCheckIfUserExists: ", e);
    }

    return hasUserLikedPost;
  }

  @Override
  public Boolean servicePostLikeAddUpvote(Long postId, String username) {
    boolean response = false;
    if (!postLikeRepository.existsByPostIdAndPostLikedBy(postId, username)) {
      PostLikeEntity postLikeEntity = new PostLikeEntity();
      postLikeEntity.setPostId(postId);
      postLikeEntity.setPostLikedBy(username);
      try {
        postLikeRepository.save(postLikeEntity);
        response = true;
      } catch (Exception e) {
        logger.error("in servicePostLikeAddUpvote ", e);
      }
    }
    return response;
  }

  @Override
  public Boolean servicePostRevertUpvote(Long postId, String username) {
    boolean response = false;
    try {
      if (postLikeRepository.existsByPostIdAndPostLikedBy(postId, username)) {
        System.out.println("here!");
        postLikeRepository.deleteByPostIdAndPostLikedBy(postId, username);
        response = true;
      }
    } catch (Exception e) {
      logger.error("in servicePostLikeAddUpvote ", e);
    }
    return response;
  }
}
