package com.beehive.composite;

import java.io.Serializable;
import java.util.Objects;

public class PostLikeId implements Serializable {
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
    public int hashCode() {
        return Objects.hash(postId, postLikedBy);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        PostLikeId that = (PostLikeId) obj;
        return Objects.equals(postId, that.postId) && Objects.equals(postLikedBy, that.postLikedBy);
    }
}
