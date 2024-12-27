package com.beehive.service;

public interface ReplyLikeService {

    Long serviceReplyLikeGetCountByReplyId(Long replyId);

    Boolean serviceReplyLikeCheckIfUserExists(Long replyId, String replyLikedBy);

    Boolean serviceReplyLikeAddUpvote(Long replyId, String username);

    Boolean serviceReplyLikeRevertUpvote(Long replyId, String username);
}
