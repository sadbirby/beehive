package com.beehive.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.beehive.dto.PostDto;
import com.beehive.dto.ReplyDto;
import com.beehive.entity.PostEntity;
import com.beehive.entity.ReplyEntity;
import com.beehive.repository.PostLikeRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired PostRepository postRepository;
    @Autowired ReplyService replyService;
    @Autowired PostLikeService postLikeService;

    Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public PostResponse servicePostGetAll(String username) {

        PostResponse response = new PostResponse();
        try {
            Iterable<PostEntity> posts = postRepository.findAll();
            List<PostDto> postDtoList = new ArrayList<>();
            posts.forEach(entity -> {
                Long numberOfReplies = replyService.serviceReplyGetCountByPostId(entity.getPostId());
                Long numberOfLikes = postLikeService.servicePostLikeGetCountByPostId(entity.getPostId());
                Boolean isPostLikedByCurrentUser = postLikeService.servicePostLikeCheckIfUserExists(entity.getPostId(), username);
                PostDto postDto = new PostDto();
                postDto.setPostId(entity.getPostId());
                postDto.setPostTitle(entity.getPostTitle());
                postDto.setPostBody(entity.getPostBody());
                postDto.setPostedBy(entity.getPostedBy());
                postDto.setPostedOn(entity.getPostedOn());
                postDto.setNumberOfLikes(numberOfLikes);
                postDto.setNumberOfReplies(numberOfReplies);
                postDto.setPostLikedByCurrentUser(isPostLikedByCurrentUser);
                postDtoList.add(postDto);
            });
            response.setPosts(postDtoList);
            response.setStatusMessage("SUCCESS");
        } catch (
                Exception e) {
            logger.error("in servicePostGetAll: ", e);
            response.setStatusMessage("FAILURE");
        }

        return response;
    }

    @Override
    public PostResponse servicePostGetAllByUserName(String username) {

        PostResponse response = new PostResponse();
        try {
            Iterable<PostEntity> posts = postRepository.findByPostedBy(username);
            List<PostDto> postDtoList = new ArrayList<>();
            posts.forEach(entity -> {
                Long numberOfReplies = replyService.serviceReplyGetCountByPostId(entity.getPostId());
                Long numberOfLikes = postLikeService.servicePostLikeGetCountByPostId(entity.getPostId());
                Boolean isPostLikedByCurrentUser = postLikeService.servicePostLikeCheckIfUserExists(entity.getPostId(), username);
                PostDto postDto = new PostDto();
                postDto.setPostId(entity.getPostId());
                postDto.setPostTitle(entity.getPostTitle());
                postDto.setPostBody(entity.getPostBody());
                postDto.setPostedBy(entity.getPostedBy());
                postDto.setPostedOn(entity.getPostedOn());
                postDto.setNumberOfLikes(numberOfLikes);
                postDto.setNumberOfReplies(numberOfReplies);
                postDto.setPostLikedByCurrentUser(isPostLikedByCurrentUser);
                postDtoList.add(postDto);
            });
            response.setPosts(postDtoList);
            response.setStatusMessage("SUCCESS");
        } catch (
                Exception e) {
            logger.error("in servicePostGetAllByUsername: ", e);
            response.setStatusMessage("FAILURE");
        }

        return response;
    }

    @Override
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
        } catch (
                Exception e) {
            logger.error("in servicePostAdd: ", e);
            response.setStatusMessage("FAILURE");
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
    public ReplyResponse servicePostAddReply(ReplyRequest request) {

        ReplyResponse response = new ReplyResponse();
        ReplyDto replyDto = request.getReply();
        ReplyEntity replyEntity = new ReplyEntity();
        try {
            Optional<PostEntity> postEntity = postRepository.findById(replyDto.getPostId());
            if (postEntity.isPresent()) {
                replyEntity.setPost(postEntity.get());
                replyEntity.setReplyBody(replyDto.getReplyBody());
                replyEntity.setRepliedBy(replyDto.getRepliedBy());
                replyEntity.setRepliedOn(new Date());
                replyService.serviceReplyAdd(replyEntity);
                response.setStatusMessage("SUCCESS");
            } else {
                throw new NullPointerException();
            }
        } catch (
                Exception e) {
            logger.error("in servicePostAddReply: ", e);
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

   /*  @Override
    public PostResponse servicePostLike(PostRequest request, String username) {

        PostResponse response = new PostResponse();
        PostDto dto = request.getPost();
        try {
            Optional<PostEntity> entity = postRepository.findById(dto.getPostId());
            if (entity.isPresent()) {
                Set<String> likedBy = entity.get().getPostLikedBy();
                if (!likedBy.contains(username)) {
                    likedBy.add(username);
                    entity.get().setPostLikedBy(likedBy);
                    entity.get().setLikes(entity.get().getLikes() + 1);
                }
            }

            postRepository.save(entity.get());
            response.setStatusMessage("SUCCESS");
        } catch (
                Exception e) {
            logger.error("in servicePostLike: ", e);
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

    @Override
    public PostResponse servicePostUnlike(PostRequest request, String username) {

        PostResponse response = new PostResponse();
        PostDto dto = request.getPost();
        try {
            Optional<PostEntity> entity = postRepository.findById(dto.getPostId());
            if (entity.isPresent() && entity.get().getLikes() != null) {
                Set<String> likedBy = entity.get().getPostLikedBy();
                Long likes = entity.get().getLikes();
                if (likedBy.contains(username) && likes > 0) {
                    likedBy.remove(username);
                    entity.get().setPostLikedBy(likedBy);
                    entity.get().setLikes(likes - 1);
                }
            }
            postRepository.save(entity.get());
            response.setStatusMessage("SUCCESS");
        } catch (
                Exception e) {
            logger.error("in servicePostUnlike: ", e);
            response.setStatusMessage("FAILURE");
        }
        return response;
    }

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
