package com.beehive.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.beehive.dto.ReplyDto;
import com.beehive.entity.ReplyEntity;
import com.beehive.repository.ReplyRepository;
import com.beehive.request.ReplyRequest;
import com.beehive.response.ReplyResponse;
import com.beehive.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired ReplyRepository replyRepository;

    Logger logger = LoggerFactory.getLogger(ReplyServiceImpl.class);

    @Override
    public Page<ReplyEntity> serviceReplyGetAllByPostId(Long postId, Integer pageNumber, Integer pageSize, String sortBy, Boolean isDescending) {
        Pageable pageable = isDescending ? PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending() ) :  PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<ReplyEntity> replyEntityList = null;
        List<ReplyDto> replyDtoList = new ArrayList<>();
        try {
            replyEntityList = replyRepository.findByPost_PostId(postId, pageable);
            /*replyEntityList.forEach(entity -> {
                ReplyDto replyDto = new ReplyDto();
                replyDto.setReplyId(entity.getReplyId());
                replyDto.setPostId(entity.getPost().getPostId());
                replyDto.setReplyBody(entity.getReplyBody());
                replyDto.setRepliedBy(entity.getRepliedBy());
                replyDto.setRepliedOn(entity.getRepliedOn());
                replyDtoList.add(replyDto);
            });
            replyResponse.setStatusMessage("SUCCESS");
            replyResponse.setReplies(replyDtoList);*/
        } catch (
                Exception e) {
            logger.error("in servicePostReplyGetAllByPostId: ", e);
        }
        return replyEntityList;
    }

    @Override
    public Long serviceReplyGetCountByPostId(Long postId) {
        Long replyCount = 0L;
        try {
            replyCount = replyRepository.countNumberOfRepliesByPostId(postId);
        } catch (
                Exception e) {
            logger.error("in servicePostReplyGetCountOfRepliesByPostId: ", e);
        }

        return replyCount;
    }

    @Override
    public Boolean serviceReplyAdd(ReplyEntity replyEntity) {
        boolean response = false;
        try {
            replyRepository.save(replyEntity);
            response = true;
        } catch (
                Exception e) {
            logger.error("in serviceReplyAdd: ", e);
        }
        return response;
    }
}
