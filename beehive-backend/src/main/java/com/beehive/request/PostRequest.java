package com.beehive.request;

import java.io.Serial;
import java.io.Serializable;

import com.beehive.dto.PostDto;

public class PostRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8300957195016445421L;

    private PostDto post;

    public PostDto getPost() {
        return post;
    }

    public void setPost(PostDto post) {
        this.post = post;
    }
}
