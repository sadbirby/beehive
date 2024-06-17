package com.beehive.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "replies")
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

    public ReplyEntity() {
    }

    public ReplyEntity(Date repliedOn, String repliedBy, String replyBody, PostEntity post, Long replyId) {
        this.repliedOn = repliedOn;
        this.repliedBy = repliedBy;
        this.replyBody = replyBody;
        this.post = post;
        this.replyId = replyId;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

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
}
