package com.beehive.service.impl;

import com.beehive.dto.PostDto;
import com.beehive.dto.ReplyDto;
import com.beehive.entity.PostEntity;
import com.beehive.entity.ReplyEntity;
import com.beehive.entity.projection.PostEntityProjection;
import com.beehive.repository.PostRepository;
import com.beehive.request.PostRequest;
import com.beehive.request.ReplyRequest;
import com.beehive.response.PostResponse;
import com.beehive.response.ReplyResponse;
import com.beehive.service.PostLikeService;
import com.beehive.service.PostService;
import com.beehive.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Post Service Implementation.
 */
@Service
public class PostServiceImpl implements PostService {

    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostLikeService postLikeService;
    private final PostRepository postRepository;
    private final ReplyService replyService;

    /**
     * Post Service Implementation Constructor.
     */
    public PostServiceImpl(
            PostLikeService postLikeService, PostRepository postRepository, ReplyService replyService) {
        this.postLikeService = postLikeService;
        this.postRepository = postRepository;
        this.replyService = replyService;
    }

    private PostEntity getPost(PostEntityProjection postEntityProjection) {
        PostEntity postEntity = new PostEntity();
        postEntity.setPostId(postEntityProjection.getPostId());
        postEntity.setPostTitle(postEntityProjection.getPostTitle());
        postEntity.setPostBody(postEntityProjection.getPostedBy());
        postEntity.setPostedBy(postEntityProjection.getPostedBy());
        postEntity.setPostedOn(postEntityProjection.getPostedOn());
        postEntity.setNumberOfLikes(postEntityProjection.getNumberOfLikes());
        postEntity.setNumberOfReplies(
                replyService.serviceReplyGetCountByPostId(postEntityProjection.getPostId()));
        return postEntity;
    }

    @Override
    public PostResponse servicePostGetByPostId(Long postId, String username) {
        PostResponse response = new PostResponse();
        List<PostDto> postDtoList = new ArrayList<>();
        try {
            Optional<PostEntityProjection> entity = postRepository.findPostByPostId(postId);
            if (entity.isPresent()) {
                PostDto postDto = new PostDto();
                Boolean isPostLikedByCurrentUser =
                        postLikeService.servicePostLikeCheckIfUserExists(entity.get().getPostId(), username);
                postDto.setPostId(entity.get().getPostId());
                postDto.setPostTitle(entity.get().getPostTitle());
                postDto.setPostBody(entity.get().getPostBody());
                postDto.setPostedBy(entity.get().getPostedBy());
                postDto.setPostedOn(entity.get().getPostedOn());
                postDto.setNumberOfLikes(entity.get().getNumberOfLikes());
                postDto.setNumberOfReplies(entity.get().getNumberOfReplies());
                postDto.setPostLikedByCurrentUser(isPostLikedByCurrentUser);

                postDtoList.add(postDto);
                response.setPosts(postDtoList);
                response.setStatusMessage("SUCCESS");
            }
        } catch (Exception e) {
            logger.error("in servicePostGetPostById: ", e);
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

    @Override
    public Page<PostDto> servicePostGetAll(
            String username, Integer pageNumber, Integer pageSize, String sortBy, Boolean isDescending) {

        // UserPrincipal userPrincipal = (UserPrincipal)
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Pageable pageable =
                isDescending
                        ? PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending())
                        : PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<PostDto> response = null;
        try {
            Page<PostEntityProjection> pagedResponse = postRepository.findAllPosts(pageable);
            response =
                    new PageImpl<>(
                            pagedResponse.getContent().stream()
                                    .map(
                                            entity -> {
                                                PostDto postDto = new PostDto();
                                                Boolean isPostLikedByCurrentUser =
                                                        postLikeService.servicePostLikeCheckIfUserExists(
                                                                entity.getPostId(), username);
                                                postDto.setPostId(entity.getPostId());
                                                postDto.setPostTitle(entity.getPostTitle());
                                                postDto.setPostBody(entity.getPostBody());
                                                postDto.setPostedBy(entity.getPostedBy());
                                                postDto.setPostedOn(entity.getPostedOn());
                                                postDto.setNumberOfLikes(entity.getNumberOfLikes());
                                                postDto.setNumberOfReplies(entity.getNumberOfReplies());
                                                postDto.setPostLikedByCurrentUser(isPostLikedByCurrentUser);

                                                return postDto;
                                            })
                                    .collect(Collectors.toList()),
                            pageable,
                            pagedResponse.getTotalElements());
            //            response.setStatusMessage("SUCCESS");
        } catch (Exception e) {
            logger.error("in servicePostGetAll: ", e);
            //            response.setStatusMessage("FAILURE");
        }

        return response;
    }

    @Override
    public Page<PostDto> servicePostGetAllByUserName(
            String username, Integer pageNumber, Integer pageSize, String sortBy, Boolean isDescending) {

        Pageable pageable =
                isDescending
                        ? PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending())
                        : PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<PostDto> response = null;
        try {
            Page<PostEntityProjection> pagedResponse =
                    postRepository.findAllPostsByPostedBy(username, pageable);
            response =
                    new PageImpl<PostDto>(
                            pagedResponse.getContent().stream()
                                    .map(
                                            entity -> {
                                                PostDto postDto = new PostDto();
                                                Boolean isPostLikedByCurrentUser =
                                                        postLikeService.servicePostLikeCheckIfUserExists(
                                                                entity.getPostId(), username);
                                                postDto.setPostId(entity.getPostId());
                                                postDto.setPostTitle(entity.getPostTitle());
                                                postDto.setPostBody(entity.getPostBody());
                                                postDto.setPostedBy(entity.getPostedBy());
                                                postDto.setPostedOn(entity.getPostedOn());
                                                postDto.setNumberOfLikes(entity.getNumberOfLikes());
                                                postDto.setNumberOfReplies(entity.getNumberOfReplies());
                                                postDto.setPostLikedByCurrentUser(isPostLikedByCurrentUser);

                                                return postDto;
                                            })
                                    .collect(Collectors.toList()),
                            pageable,
                            pagedResponse.getTotalElements());
            //            response.setStatusMessage("SUCCESS");
        } catch (Exception e) {
            logger.error("in servicePostGetAllByUsername: ", e);
            //            response.setStatusMessage("FAILURE");
        }

        return response;
    }

  /* @Override
  public PostResponse servicePostDelete(String userName, String postId) {

      PostResponse response = new PostResponse();
      try {
          postRepository.deleteById(postId);
          response.setStatusMessage("SUCCESS");
      } catch (
              Exception e) {
          logger.error("in servicePostDelete: ", e);
          response.setStatusMessage("FAILURE");
      }
      return response;
  } */

    @Override
    @Transactional(readOnly = false)
    public PostResponse servicePostAdd(PostRequest request, String username) {

        PostResponse response = new PostResponse();
        PostDto postDto = request.getPost();
        PostEntity postEntity = new PostEntity();
        try {
            postEntity.setPostTitle(postDto.getPostTitle());
            postEntity.setPostBody(postDto.getPostBody());
            postEntity.setPostedBy(username);
            postEntity.setPostedOn(new Date());
            postEntity.setNumberOfLikes(0L);
            postEntity.setNumberOfReplies(0L);
            postRepository.save(postEntity);
            response.setStatusMessage("SUCCESS");
        } catch (Exception e) {
            logger.error("in servicePostAdd: ", e);
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

    @Override
    @Transactional(readOnly = false)
    public ReplyResponse servicePostAddReply(ReplyRequest request) {

        ReplyResponse response = new ReplyResponse();
        ReplyDto replyDto = request.getReply();
        ReplyEntity replyEntity = new ReplyEntity();
        try {
            Optional<PostEntityProjection> postEntityProjection =
                    postRepository.findPostByPostId(replyDto.getPostId());
            if (postEntityProjection.isPresent()) {
                PostEntity postEntity = getPost(postEntityProjection.get());
                logger.info("NUMBER OF LIKES: {}", postEntity.getNumberOfLikes());
                replyEntity.setPost(postEntity);
                replyEntity.setReplyBody(replyDto.getReplyBody());
                replyEntity.setRepliedBy(replyDto.getRepliedBy());
                replyEntity.setRepliedOn(new Date());
                replyEntity.setNumberOfLikes(0L);
                ReplyResponse result = replyService.serviceReplyAdd(replyEntity);
                if (result.getStatusMessage().equals("SUCCESS")) {
                    response.setStatusMessage("SUCCESS");
                    Long numberOfReplies = replyService.serviceReplyGetCountByPostId(replyDto.getPostId());
                    postRepository.updateNumberOfRepliesByPostId(replyDto.getPostId(), numberOfReplies);
                }
            } else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            logger.error("in servicePostAddReply: ", e);
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

    @Override
    @Transactional(readOnly = false)
    public PostResponse servicePostUpvote(Long postId, String username) {
        PostResponse response = new PostResponse();
        try {
            Optional<PostEntityProjection> postEntityProjection = postRepository.findPostByPostId(postId);
            if (postEntityProjection.isPresent()) {
                Boolean result = postLikeService.servicePostLikeAddUpvote(postId, username);
                if (result) {
                    Long newNumberOfLikes = postLikeService.servicePostLikeGetCountByPostId(postId);
                    postRepository.updateNumberOfLikesByPostId(postId, newNumberOfLikes);
                    response.setStatusMessage("SUCCESS");
                }
            }
        } catch (Exception e) {
            logger.error("in servicePostUpvote: ", e);
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

    @Override
    @Transactional(readOnly = false)
    public PostResponse servicePostRevertUpvote(Long postId, String username) {
        PostResponse response = new PostResponse();
        try {
            Optional<PostEntityProjection> postEntityProjection = postRepository.findPostByPostId(postId);
            if (postEntityProjection.isPresent()) {
                Boolean result = postLikeService.servicePostLikeRevertUpvote(postId, username);
                if (result) {
                    Long newNumberOfLikes = postLikeService.servicePostLikeGetCountByPostId(postId);
                    postRepository.updateNumberOfLikesByPostId(postId, newNumberOfLikes);
                    response.setStatusMessage("SUCCESS");
                }
            }
        } catch (Exception e) {
            logger.error("in servicePostRevertUpvote: ", e);
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

  /*
  @Override
  public PostResponse servicePostUpdate(PostRequest request) {

      PostResponse response = new PostResponse();
      PostDto dto = request.getPost();
      try {
          Optional<PostEntity> entity = postRepository.findById(dto.getPostId());
          entity.get().setPostTitle(dto.getPostTitle());
          postRepository.save(entity.get());
          response.setStatusMessage("SUCCESS");
      } catch (
              Exception e) {
          logger.error("in servicePostUpdate: ", e);
          response.setStatusMessage("FAILURE");
      }
      return response;
  }

  @Override
  public PostResponse servicePostFindPopularOfLastMonth() {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.MONTH, -1);
      Date startDate = cal.getTime();
      Date endDate = new Date();
      System.out.println(startDate + " - " + endDate);
      PostResponse response = new PostResponse();
      try {
          Iterable<PostEntity> tweets = postRepository.findPopularTweets(startDate, endDate);
          List<PostDto> postDto = new ArrayList<>();
          tweets.forEach(entity -> {
              PostDto tweet = new PostDto();
              tweet.setPostTitle(entity.getPostTitle());
              tweet.setPostId(entity.getPostId());
              tweet.setPostedBy(entity.getPostedBy());
              tweet.setLike(entity.getLikes());
              tweet.setPostReplies(entity.getPostReplies());
              tweet.setPostLikedBy(entity.getPostLikedBy());
              tweet.setPostedOn(entity.getPostedOn());
              postDto.add(tweet);
          });
          response.setTweetsDto(postDto);
          response.setStatusMessage("SUCCESS");
      } catch (
              Exception e) {
          logger.error("in servicePostFindPopularOfLastMonth: ", e);
          response.setStatusMessage("FAILURE");
      }

      return response;
  }*/
}
