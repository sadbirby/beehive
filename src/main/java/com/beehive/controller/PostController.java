package com.beehive.controller;

import com.beehive.dto.PostDto;
import com.beehive.dto.ReplyDto;
import com.beehive.request.PostRequest;
import com.beehive.request.ReplyRequest;
import com.beehive.response.PostResponse;
import com.beehive.response.ReplyResponse;
import com.beehive.service.PostService;
import com.beehive.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1.0/posts")
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    private final ReplyService replyService;

    public PostController(PostService postService, ReplyService replyService) {
        this.postService = postService;
        this.replyService = replyService;
    }

    @GetMapping()
    public PostResponse getPostById(@RequestParam Long postId, @RequestParam String username) {
        PostResponse response = postService.servicePostGetByPostId(postId, username);
        logger.info("Post Controller in getPostById() call {}", response.getStatusMessage());
        return response;
    }

    @GetMapping(value = "/all")
    public Page<PostDto> getAllPosts(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "postedOn") String sortBy,
            @RequestParam(defaultValue = "true") Boolean isDescending) {
        Page<PostDto> response =
                postService.servicePostGetAll(username, pageNumber, pageSize, sortBy, isDescending);
        logger.info("Post Controller in get All Posts() call ");
        return response;
    }

    @GetMapping(value = "/{username}/all")
    public Page<PostDto> getAllPostsByUser(
            @PathVariable("username") String username,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "postedOn") String sortBy,
            @RequestParam(defaultValue = "true") Boolean isDescending) {
        Page<PostDto> response =
                postService.servicePostGetAllByUserName(
                        username, pageNumber, pageSize, sortBy, isDescending);
        logger.info("Post Controller in get All Posts By Username() call ");
        return response;
    }

    @GetMapping(value = "/{postId}/replies")
    public Page<ReplyDto> getAllRepliesByPost(
            @PathVariable("postId") Long postId,
            @RequestParam String username,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "repliedOn") String sortBy,
            @RequestParam(defaultValue = "true") Boolean isDescending) {
        Page<ReplyDto> response =
                replyService.serviceReplyGetAllByPostId(
                        postId, username, pageNumber, pageSize, sortBy, isDescending);
        logger.info("Post Controller in get All Replies() call");
        return response;
    }

    @PostMapping(value = "/add")
    public PostResponse addPost(@RequestBody PostRequest request, @RequestParam String username) {
        PostResponse response = postService.servicePostAdd(request, username);
        logger.info("Post Controller in addPost() call {}", response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "/reply")
    public ReplyResponse replyToPost(@RequestBody ReplyRequest request) {
        ReplyResponse response = postService.servicePostAddReply(request);
        logger.info("Post Controller in replyToPost() call {}", response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "/{postId}/like")
    public PostResponse upvoteAPost(
            @RequestParam String username, @PathVariable("postId") Long postId) {
        PostResponse response = postService.servicePostUpvote(postId, username);
        logger.info("Post Controller in upvoteAPost() call {}", response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "/{postId}/unlike")
    public PostResponse downvoteAPost(
            @RequestParam String username, @PathVariable("postId") Long postId) {
        PostResponse response = postService.servicePostRevertUpvote(postId, username);
        logger.info("Post Controller in downvoteAPost() call {}", response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "reply/{replyId}/like")
    public ReplyResponse upvoteAReply(
            @RequestParam String username, @PathVariable("replyId") Long replyId) {
        ReplyResponse response = replyService.serviceReplyUpvote(replyId, username);
        logger.info("Reply Controller in upvoteAReply() call {}", response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "reply/{replyId}/unlike")
    public ReplyResponse downvoteAReply(
            @RequestParam String username, @PathVariable("replyId") Long replyId) {
        ReplyResponse response = replyService.serviceReplyRevertUpvote(replyId, username);
        logger.info("Reply Controller in downvoteAReply() call {}", response.getStatusMessage());
        return response;
    }

  /*@DeleteMapping(path = "/{username}/delete/{id}")
  public PostResponse deletePost(@PathVariable("username") String username, @PathVariable("id") String tweetId) {
      PostResponse response = postService.servicePostDelete(username, tweetId);
      logger.info("Post Controller" + " in deletePost() call " + response.getStatusMessage());
      return response;
  }

  @PostMapping(value = "/update")
  public PostResponse updatePost(@RequestBody PostRequest request) {
      //producer.sendMessage(request.getPost().getPost());
      PostResponse response = postService.servicePostUpdate(request);
      logger.info("Post Controller" + " in updatePost() call " + response.getStatusMessage());
      return response;
  }*/
}
