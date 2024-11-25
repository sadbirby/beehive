package com.beehive.service.impl;

import com.beehive.entity.ReplyLikeEntity;
import com.beehive.repository.ReplyLikeRepository;
import com.beehive.service.ReplyLikeService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReplyLikeServiceImpl implements ReplyLikeService {

    private final ReplyLikeRepository replyLikeRepository;
    private final Logger logger = LoggerFactory.getLogger(ReplyLikeServiceImpl.class);

    public ReplyLikeServiceImpl(ReplyLikeRepository replyLikeRepository) {
        this.replyLikeRepository = replyLikeRepository;
    }

    @Override
    public Long serviceReplyLikeGetCountByReplyId(Long replyId) {
        Long numberOfLikes = 0L;
        try {
            numberOfLikes = replyLikeRepository.countByReplyId(replyId);
        } catch (Exception e) {
            logger.error("in serviceReplyLikeGetCountByReplyId: ", e);
        }
        return numberOfLikes;
    }

    @Override
    public Boolean serviceReplyLikeCheckIfUserExists(Long replyId, String replyLikedBy) {
        Boolean hasUserLikedReply = false;
        try {
            hasUserLikedReply = replyLikeRepository.existsByReplyIdAndReplyLikedBy(replyId, replyLikedBy);
        } catch (Exception e) {
            logger.error("in serviceReplyLikeCheckIfUserExists: ", e);
        }
        return hasUserLikedReply;
    }

    @Override
    @Transactional
    public Boolean serviceReplyLikeAddUpvote(Long replyId, String username) {
        boolean response = false;
        if (!replyLikeRepository.existsByReplyIdAndReplyLikedBy(replyId, username)) {
            ReplyLikeEntity replyLikeEntity = new ReplyLikeEntity();
            replyLikeEntity.setReplyId(replyId);
            replyLikeEntity.setReplyLikedBy(username);
            try {
                replyLikeRepository.save(replyLikeEntity);
                response = true;
            } catch (Exception e) {
                logger.error("in serviceReplyLikeAddUpvote ", e);
            }
        }
        return response;
    }

    @Override
    public Boolean serviceReplyLikeRevertUpvote(Long replyId, String username) {
        boolean response = false;
        try {
            if (replyLikeRepository.existsByReplyIdAndReplyLikedBy(replyId, username)) {
                replyLikeRepository.deleteByReplyIdAndReplyLikedBy(replyId, username);
                response = true;
            }
        } catch (Exception e) {
            logger.error("in serviceReplyLikeAddUpvote ", e);
        }
        return response;
    }
}
