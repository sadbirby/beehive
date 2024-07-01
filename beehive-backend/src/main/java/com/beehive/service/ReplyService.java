package com.beehive.service;

import com.beehive.dto.ReplyDto;
import com.beehive.entity.ReplyEntity;
import com.beehive.request.ReplyRequest;
import com.beehive.response.ReplyResponse;
import org.springframework.data.domain.Page;

public interface ReplyService {

    Boolean serviceReplyAdd(ReplyEntity replyEntity);

    Page<ReplyEntity> serviceReplyGetAllByPostId(Long postId, Integer pageNumber, Integer pageSize, String sortBy, Boolean isDescending);

    Long serviceReplyGetCountByPostId(Long postId);
}
