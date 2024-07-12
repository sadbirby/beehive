package com.beehive.service;

import com.beehive.entity.ReplyEntity;
import org.springframework.data.domain.Page;

public interface ReplyService {

  Boolean serviceReplyAdd(ReplyEntity replyEntity);

  Page<ReplyEntity> serviceReplyGetAllByPostId(
      Long postId, Integer pageNumber, Integer pageSize, String sortBy, Boolean isDescending);

  Long serviceReplyGetCountByPostId(Long postId);
}
