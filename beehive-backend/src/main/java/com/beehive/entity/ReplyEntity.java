package com.beehive.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "replies", indexes = @Index(name = "index_post_id", columnList = "postId"))
public class ReplyEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long replyId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "postId", nullable = false)
  private PostEntity post;

  @Column(columnDefinition = "LONGTEXT")
  private String replyBody;

  private String repliedBy;
  private Date repliedOn;
  private Long numberOfLikes;

  @OneToMany(
      mappedBy = "reply",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
      //          , orphanRemoval = true
      )
  private Set<ReplyLikeEntity> replyLikedBy = new HashSet<>();

  public ReplyEntity() {}

  public ReplyEntity(
      Long replyId,
      PostEntity post,
      String replyBody,
      String repliedBy,
      Date repliedOn,
      Set<ReplyLikeEntity> replyLikedBy) {
    this.replyId = replyId;
    this.post = post;
    this.replyBody = replyBody;
    this.repliedBy = repliedBy;
    this.repliedOn = repliedOn;
    this.replyLikedBy = replyLikedBy;
  }

  public Long getReplyId() {
    return replyId;
  }

  public void setReplyId(Long replyId) {
    this.replyId = replyId;
  }

  @JsonBackReference
  public PostEntity getPost() {
    return post;
  }

  public void setPost(PostEntity post) {
    this.post = post;
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

  public Long getNumberOfLikes() {
    return numberOfLikes;
  }

  public void setNumberOfLikes(Long numberOfLikes) {
    this.numberOfLikes = numberOfLikes;
  }

  @JsonManagedReference
  public Set<ReplyLikeEntity> getReplyLikedBy() {
    return replyLikedBy;
  }

  public void setReplyLikedBy(Set<ReplyLikeEntity> replyLikedBy) {
    this.replyLikedBy = replyLikedBy;
  }
}
