package com.beehive.controller;

import com.beehive.dto.PostDto;
import com.beehive.dto.ReplyDto;
import com.beehive.entity.PostEntity;
import com.beehive.entity.ReplyEntity;
import com.beehive.request.PostRequest;
import com.beehive.request.ReplyRequest;
import com.beehive.response.PostResponse;
import com.beehive.response.ReplyResponse;
import com.beehive.service.PostService;
import com.beehive.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1.0/posts")
@CrossOrigin("*")
public class PostController {

    @Autowired PostService postService;

    @Autowired ReplyService replyService;

    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @GetMapping(value = "{username}/all")
    public Page<PostDto> getAllPosts(@PathVariable("username") String username,
                                     @RequestParam(defaultValue = "0") Integer pageNumber,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "postedOn") String sortBy,
                                     @RequestParam(defaultValue = "true") Boolean isDescending) {
        Page<PostDto> response = postService.servicePostGetAll(username, pageNumber, pageSize, sortBy, isDescending);
        logger.info("Post Controller" + " in get All Posts() call ");
        return response;
    }

    @GetMapping(value = "/{username}/created")
    public PostResponse getAllPostsByUser(@PathVariable("username") String username) {
        PostResponse response = postService.servicePostGetAllByUserName(username);
        logger.info("Post Controller" + " in get All Posts() call " + response.getStatusMessage());
        return response;
    }

    @GetMapping(value = "/{postId}/replies")
    public Page<ReplyEntity> getAllPostsByUser(@PathVariable("postId") Long postId,
                                               @RequestParam(defaultValue = "0") Integer pageNumber,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "repliedOn") String sortBy,
                                               @RequestParam(defaultValue = "true") Boolean isDescending) {
        Page<ReplyEntity> response = replyService.serviceReplyGetAllByPostId(postId, pageNumber, pageSize, sortBy, isDescending);
        logger.info("Post Controller" + " in get All Replies() call ");
        return response;
    }

    @PostMapping(value = "/{username}/add")
    public PostResponse addPost(@RequestBody PostRequest request, @PathVariable("username") String username) {
        //producer.sendMessage(request.getPost().getPost());
        PostResponse response = postService.servicePostAdd(request, username);
        logger.info("Post Controller" + " in addPost() call " + response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "/reply")
    public ReplyResponse replyToPost(@RequestBody ReplyRequest request) {
        //producer.sendMessage(request.getPost().getReply().get(0).getReplied());
        ReplyResponse response = postService.servicePostAddReply(request);
        logger.info("Post Controller" + " in replyToPost() call " + response.getStatusMessage());
        return response;
    }

    /*@GetMapping(value = "/popular")
    public PostResponse getPopularPosts() {
        PostResponse response = postService.servicePostFindPopularOfLastMonth();
        logger.info("Post Controller" + " in popular Posts() call " + response.getStatusMessage());
        return response;
    }

    @DeleteMapping(path = "/{username}/delete/{id}")
    public PostResponse deletePost(@PathVariable("username") String username, @PathVariable("id") String tweetId) {
        PostResponse response = postService.servicePostDelete(username, tweetId);
        logger.info("Post Controller" + " in deletePost() call " + response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "/{username}/like")
    public PostResponse likeAPost(@PathVariable String username, @RequestBody PostRequest request) {
        PostResponse response = postService.servicePostLike(request, username);
        logger.info("Post Controller" + " in likeAPost() call " + response.getStatusMessage());
        return response;
    }

    @PostMapping(value = "/{username}/unlike")
    public PostResponse unlikeAPost(@PathVariable String username, @RequestBody PostRequest request) {
        PostResponse response = postService.servicePostUnlike(request, username);
        logger.info("Post Controller" + " in unlikeAPost() call " + response.getStatusMessage());
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
