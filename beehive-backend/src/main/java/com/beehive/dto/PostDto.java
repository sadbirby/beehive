package com.beehive.dto;

import java.util.Date;
import java.util.Set;

public class PostDto {

  private Long postId;
  private String postTitle;
  private String postBody;
  private String postedBy;
  private Date postedOn;
  private Long numberOfLikes;
  private Long numberOfReplies;
  private Boolean isPostLikedByCurrentUser;
  private Set<String> postLikedBy;
  private Set<ReplyDto> postReplies;

  public PostDto() {}

  public PostDto(
      Long postId,
      String postTitle,
      String postBody,
      String postedBy,
      Date postedOn,
      Long numberOfLikes,
      Long numberOfReplies,
      Boolean isPostLikedByCurrentUser,
      Set<String> postLikedBy,
      Set<ReplyDto> postReplies) {
    this.postId = postId;
    this.postTitle = postTitle;
    this.postBody = postBody;
    this.postedBy = postedBy;
    this.postedOn = postedOn;
    this.numberOfLikes = numberOfLikes;
    this.numberOfReplies = numberOfReplies;
    this.isPostLikedByCurrentUser = isPostLikedByCurrentUser;
    this.postLikedBy = postLikedBy;
    this.postReplies = postReplies;
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public String getPostTitle() {
    return postTitle;
  }

  public void setPostTitle(String postTitle) {
    this.postTitle = postTitle;
  }

  public String getPostBody() {
    return postBody;
  }

  public void setPostBody(String postBody) {
    this.postBody = postBody;
  }

  public String getPostedBy() {
    return postedBy;
  }

  public void setPostedBy(String postedBy) {
    this.postedBy = postedBy;
  }

  public Date getPostedOn() {
    return postedOn;
  }

  public void setPostedOn(Date postedOn) {
    this.postedOn = postedOn;
  }

  public Long getNumberOfLikes() {
    return numberOfLikes;
  }

  public void setNumberOfLikes(Long numberOfLikes) {
    this.numberOfLikes = numberOfLikes;
  }

  public Long getNumberOfReplies() {
    return numberOfReplies;
  }

  public void setNumberOfReplies(Long numberOfReplies) {
    this.numberOfReplies = numberOfReplies;
  }

  public Boolean getPostLikedByCurrentUser() {
    return isPostLikedByCurrentUser;
  }

  public void setPostLikedByCurrentUser(Boolean postLikedByCurrentUser) {
    isPostLikedByCurrentUser = postLikedByCurrentUser;
  }

  public Set<String> getPostLikedBy() {
    return postLikedBy;
  }

  public void setPostLikedBy(Set<String> postLikedBy) {
    this.postLikedBy = postLikedBy;
  }

  public Set<ReplyDto> getPostReplies() {
    return postReplies;
  }

  public void setPostReplies(Set<ReplyDto> postReplies) {
    this.postReplies = postReplies;
  }
}
