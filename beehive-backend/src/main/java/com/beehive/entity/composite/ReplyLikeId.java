package com.beehive.entity.composite;

import java.util.Objects;

public class ReplyLikeId {
  private Long replyId;
  private String replyLikedBy;

  public ReplyLikeId() {}

  public Long getReplyId() {
    return replyId;
  }

  public void setReplyId(Long replyId) {
    this.replyId = replyId;
  }

  public String getReplyLikedBy() {
    return replyLikedBy;
  }

  public void setReplyLikedBy(String replyLikedBy) {
    this.replyLikedBy = replyLikedBy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ReplyLikeId that = (ReplyLikeId) o;
    return Objects.equals(replyId, that.replyId) && Objects.equals(replyLikedBy, that.replyLikedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(replyId, replyLikedBy);
  }
}
