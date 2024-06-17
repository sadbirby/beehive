package com.beehive.service.impl;

import com.beehive.repository.PostLikeRepository;
import com.beehive.service.PostLikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    @Autowired PostLikeRepository postLikeRepository;

    Logger logger = LoggerFactory.getLogger(PostLikeServiceImpl.class);

    @Override
    public Long servicePostLikeGetCountByPostId(Long postId) {
        Long numberOfLikes = 0L;
        try {
            numberOfLikes = postLikeRepository.countByPostId(postId);
        } catch (
                Exception e) {
            logger.error("in servicePostLikeGetCountByPostId: ", e);
        }

        return numberOfLikes;
    }

    @Override
    public Boolean servicePostLikeCheckIfUserExists(Long postId, String likedBy) {
        Boolean hasUserLikedPost = false;
        try {
            hasUserLikedPost = postLikeRepository.existsByPostIdAndPostLikedBy(postId, likedBy);
        } catch (
                Exception e) {
            logger.error("in servicePostLikeCheckIfUserExists: ", e);
        }

        return hasUserLikedPost;
    }
}
