package com.beehive.service;

import com.beehive.dto.ReplyDto;
import com.beehive.entity.ReplyEntity;
import com.beehive.response.ReplyResponse;
import org.springframework.data.domain.Page;

public interface ReplyService {

    ReplyResponse serviceReplyAdd(ReplyEntity replyEntity);

    Page<ReplyDto> serviceReplyGetAllByPostId(
            Long postId,
            String username,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            Boolean isDescending);

    Long serviceReplyGetCountByPostId(Long postId);

    ReplyResponse serviceReplyUpvote(Long replyId, String username);

    ReplyResponse serviceReplyRevertUpvote(Long replyId, String username);
}
