package com.beehive.service.impl;

import com.beehive.repository.PostLikeRepository;
import com.beehive.service.PostLikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final Logger logger = LoggerFactory.getLogger(PostLikeServiceImpl.class);

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
            logger.info("{}: {}", postId, username);
            try {
                int count = postLikeRepository.saveEntity(postId, username);
                response = count > 0;
            } catch (Exception e) {
                logger.error("in servicePostLikeAddUpvote ", e);
            }
        }
        return response;
    }

    @Override
    public Boolean servicePostLikeRevertUpvote(Long postId, String username) {
        boolean response = false;
        try {
            if (postLikeRepository.existsByPostIdAndPostLikedBy(postId, username)) {
                int count = postLikeRepository.deleteByPostIdAndPostLikedBy(postId, username);
                response = count > 0;
            }
        } catch (Exception e) {
            logger.error("in servicePostLikeAddUpvote ", e);
        }
        return response;
    }
}
