package com.beehive.web.request;

import com.beehive.domain.dto.PostDto;

public class PostRequest {

    private PostDto post;

    public PostDto getPost() {
        return post;
    }

    public void setPost(PostDto post) {
        this.post = post;
    }
}
