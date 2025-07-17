package com.beehive.domain.service;

import com.beehive.domain.dto.ReplyDto;
import com.beehive.domain.entity.ReplyEntity;
import com.beehive.web.response.ReplyResponse;
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
