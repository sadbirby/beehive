package com.beehive.service;

import com.beehive.dto.PostDto;
import com.beehive.request.PostRequest;
import com.beehive.request.ReplyRequest;
import com.beehive.response.PostResponse;
import com.beehive.response.ReplyResponse;
import org.springframework.data.domain.Page;

public interface PostService {

    PostResponse servicePostGetByPostId(Long postId, String username);

    Page<PostDto> servicePostGetAll(
            String username, Integer pageNumber, Integer pageSize, String sortBy, Boolean isDescending);

    Page<PostDto> servicePostGetAllByUserName(
            String username, Integer pageNumber, Integer pageSize, String sortBy, Boolean isDescending);

    PostResponse servicePostAdd(PostRequest request, String username);

    ReplyResponse servicePostAddReply(ReplyRequest request);

    PostResponse servicePostUpvote(Long postId, String username);

    PostResponse servicePostRevertUpvote(Long postId, String username);

  /* PostResponse servicePostDelete(String userName, String postId);
  PostResponse servicePostUpdate(PostRequest request);
  PostResponse servicePostFindPopularOfLastMonth();*/
}
