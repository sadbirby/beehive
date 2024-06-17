package com.beehive.service;

import com.beehive.entity.ReplyEntity;
import com.beehive.request.ReplyRequest;
import com.beehive.response.ReplyResponse;

public interface ReplyService {

    Boolean serviceReplyAdd(ReplyEntity replyEntity);

    ReplyResponse serviceReplyGetAllByPostId(Long postId);

    Long serviceReplyGetCountByPostId(Long postId);
}
