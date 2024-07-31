package com.beehive.service.impl;

import com.beehive.dto.ReplyDto;
import com.beehive.entity.ReplyEntity;
import com.beehive.entity.projection.ReplyEntityProjection;
import com.beehive.repository.ReplyRepository;
import com.beehive.response.ReplyResponse;
import com.beehive.service.ReplyLikeService;
import com.beehive.service.ReplyService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {

  private final Logger logger = LoggerFactory.getLogger(ReplyServiceImpl.class);
  private final ReplyLikeService replyLikeService;
  private final ReplyRepository replyRepository;

  public ReplyServiceImpl(ReplyLikeService replyLikeService, ReplyRepository replyRepository) {
    this.replyLikeService = replyLikeService;
    this.replyRepository = replyRepository;
  }

  private ReplyEntity getReply(ReplyEntityProjection replyEntityProjection) {
    ReplyEntity replyEntity = new ReplyEntity();
    replyEntity.setReplyId(replyEntityProjection.getReplyId());
    replyEntity.setReplyBody(replyEntityProjection.getReplyBody());
    replyEntity.setRepliedBy(replyEntityProjection.getRepliedBy());
    replyEntity.setRepliedOn(replyEntityProjection.getRepliedOn());
    return replyEntity;
  }

  @Override
  public Page<ReplyDto> serviceReplyGetAllByPostId(
      Long postId,
      String username,
      Integer pageNumber,
      Integer pageSize,
      String sortBy,
      Boolean isDescending) {
    Pageable pageable =
        isDescending
            ? PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending())
            : PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
    Page<ReplyDto> response = null;
    try {
      Page<ReplyEntityProjection> pagedResponse =
          replyRepository.findAllRepliesByPostId(postId, pageable);
      response =
          new PageImpl<>(
              pagedResponse.getContent().stream()
                  .map(
                      entity -> {
                        ReplyDto replyDto = new ReplyDto();
                        replyDto.setReplyId(entity.getReplyId());
                        replyDto.setReplyBody(entity.getReplyBody());
                        replyDto.setRepliedBy(entity.getRepliedBy());
                        replyDto.setRepliedOn(entity.getRepliedOn());
                        replyDto.setNumberOfLikes(entity.getNumberOfLikes());
                        replyDto.setReplyLikedByCurrentUser(
                            replyLikeService.serviceReplyLikeCheckIfUserExists(
                                entity.getReplyId(), username));
                        return replyDto;
                      })
                  .collect(Collectors.toList()),
              pageable,
              pagedResponse.getTotalElements());
    } catch (Exception e) {
      logger.error("in serviceReplyGetAllByPostId: ", e);
    }
    return response;
  }

  @Override
  public Long serviceReplyGetCountByPostId(Long postId) {
    Long replyCount = 0L;
    try {
      replyCount = replyRepository.countNumberOfRepliesByPostId(postId);
    } catch (Exception e) {
      logger.error("in serviceReplyGetCountOfRepliesByPostId: ", e);
    }

    return replyCount;
  }

  @Override
  @Transactional
  public ReplyResponse serviceReplyAdd(ReplyEntity replyEntity) {
    ReplyResponse response = new ReplyResponse();
    try {
      replyRepository.saveAndFlush(replyEntity);
      response.setStatusMessage("SUCCESS");
    } catch (Exception e) {
      logger.error("in serviceReplyAdd: ", e);
      response.setStatusMessage("FAILURE");
    }
    return response;
  }

  @Override
  public ReplyResponse serviceReplyUpvote(Long replyId, String username) {
    ReplyResponse response = new ReplyResponse();
    try {
      Optional<ReplyEntityProjection> replyEntityProjection =
          replyRepository.findReplyByReplyId(replyId);
      if (replyEntityProjection.isPresent()) {
        Boolean result = replyLikeService.serviceReplyLikeAddUpvote(replyId, username);
        if (result) {
          Long newNumberOfLikes = replyLikeService.serviceReplyLikeGetCountByReplyId(replyId);
          replyRepository.updateNumberOfLikesByReplyId(replyId, newNumberOfLikes);
          response.setStatusMessage("SUCCESS");
        }
      }
    } catch (Exception e) {
      logger.error("in ServiceReplyUpvote: ", e);
      response.setStatusMessage("FAILURE");
    }
    return response;
  }

  @Override
  public ReplyResponse serviceReplyRevertUpvote(Long replyId, String username) {

    ReplyResponse response = new ReplyResponse();
    try {
      Optional<ReplyEntityProjection> replyEntityProjection =
          replyRepository.findReplyByReplyId(replyId);
      if (replyEntityProjection.isPresent()) {
        Boolean result = replyLikeService.serviceReplyLikeRevertUpvote(replyId, username);
        if (result) {
          Long newNumberOfLikes = replyLikeService.serviceReplyLikeGetCountByReplyId(replyId);
          replyRepository.updateNumberOfLikesByReplyId(replyId, newNumberOfLikes);
          response.setStatusMessage("SUCCESS");
        }
      }
    } catch (Exception e) {
      logger.error("in ServiceReplyRevertUpvote: ", e);
      response.setStatusMessage("FAILURE");
    }
    return response;
  }
}
