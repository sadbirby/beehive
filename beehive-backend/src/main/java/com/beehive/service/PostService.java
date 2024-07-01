package com.beehive.service;

import com.beehive.dto.PostDto;
import com.beehive.request.PostRequest;
import com.beehive.request.ReplyRequest;
import com.beehive.response.PostResponse;
import com.beehive.response.ReplyResponse;
import org.springframework.data.domain.Page;

public interface PostService {

    Page<PostDto> servicePostGetAll(String username, Integer pageNumber, Integer pageSize, String sortBy, Boolean isDescending);

    PostResponse servicePostGetAllByUserName(String username);


    PostResponse servicePostAdd(PostRequest request, String username);

    ReplyResponse servicePostAddReply(ReplyRequest request);

   /* PostResponse servicePostDelete(String userName, String postId);

    PostResponse servicePostLike(PostRequest request, String username);

    PostResponse servicePostUnlike(PostRequest request, String username);

    PostResponse servicePostUpdate(PostRequest request);

    PostResponse servicePostFindPopularOfLastMonth();*/
}
