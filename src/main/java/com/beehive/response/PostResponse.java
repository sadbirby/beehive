package com.beehive.response;

import com.beehive.dto.PostDto;

import java.util.List;

public class PostResponse {

    private List<PostDto> posts;
    private String statusMessage;

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
