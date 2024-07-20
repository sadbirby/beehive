package com.beehive.dto;

import jakarta.persistence.Embeddable;
import java.util.Date;
import java.util.Set;

@Embeddable
public class ReplyDto {

  private Long replyId;
  private Long postId;
  private String replyBody;
  private String repliedBy;
  private Date repliedOn;
  private Long numberOfLikes;
  private Boolean isReplyLikedByCurrentUser;
  private Set<String> replyLikedBy;

  public ReplyDto() {}

  public ReplyDto(
      Boolean isReplyLikedByCurrentUser,
      Long numberOfLikes,
      Long postId,
      String repliedBy,
      Date repliedOn,
      String replyBody,
      Long replyId,
      Set<String> replyLikedBy) {
    this.isReplyLikedByCurrentUser = isReplyLikedByCurrentUser;
    this.numberOfLikes = numberOfLikes;
    this.postId = postId;
    this.repliedBy = repliedBy;
    this.repliedOn = repliedOn;
    this.replyBody = replyBody;
    this.replyId = replyId;
    this.replyLikedBy = replyLikedBy;
  }

  public Long getReplyId() {
    return replyId;
  }

  public void setReplyId(Long replyId) {
    this.replyId = replyId;
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public String getReplyBody() {
    return replyBody;
  }

  public void setReplyBody(String replyBody) {
    this.replyBody = replyBody;
  }

  public String getRepliedBy() {
    return repliedBy;
  }

  public void setRepliedBy(String repliedBy) {
    this.repliedBy = repliedBy;
  }

  public Date getRepliedOn() {
    return repliedOn;
  }

  public void setRepliedOn(Date repliedOn) {
    this.repliedOn = repliedOn;
  }

  public Boolean getReplyLikedByCurrentUser() {
    return isReplyLikedByCurrentUser;
  }

  public void setReplyLikedByCurrentUser(Boolean replyLikedByCurrentUser) {
    isReplyLikedByCurrentUser = replyLikedByCurrentUser;
  }

  public Long getNumberOfLikes() {
    return numberOfLikes;
  }

  public void setNumberOfLikes(Long numberOfLikes) {
    this.numberOfLikes = numberOfLikes;
  }

  public Set<String> getReplyLikedBy() {
    return replyLikedBy;
  }

  public void setReplyLikedBy(Set<String> replyLikedBy) {
    this.replyLikedBy = replyLikedBy;
  }
}
