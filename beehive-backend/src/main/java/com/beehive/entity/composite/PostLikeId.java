package com.beehive.entity.composite;

import java.util.Objects;

public class PostLikeId {
    private Long postId;
    private String postLikedBy;

    public PostLikeId() {
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostLikedBy() {
        return postLikedBy;
    }

    public void setPostLikedBy(String postLikedBy) {
        this.postLikedBy = postLikedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostLikeId that = (PostLikeId) o;
        return Objects.equals(postId, that.postId) && Objects.equals(postLikedBy, that.postLikedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, postLikedBy);
    }
}
