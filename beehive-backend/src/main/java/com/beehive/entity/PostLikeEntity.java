package com.beehive.entity;

import com.beehive.composite.PostLikeId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(PostLikeId.class)
@Table(name = "post_likes")
public class PostLikeEntity {
  @Id private Long postId;

  @Id private String postLikedBy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "postId", insertable = false, updatable = false)
  private PostEntity post;

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

  @JsonBackReference
  public PostEntity getPost() {
    return post;
  }

  public void setPost(PostEntity post) {
    this.post = post;
  }
}
