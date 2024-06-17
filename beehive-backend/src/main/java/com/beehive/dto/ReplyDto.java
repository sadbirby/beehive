package com.beehive.dto;

import java.util.Date;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReplyDto {

    private Long replyId;
    private Long postId;
    private String replyBody;
    private String repliedBy;
    private Date repliedOn;

    public ReplyDto() {
    }

    public ReplyDto(Long replyId, Long postId, String replyBody, String repliedBy, Date repliedOn) {
        this.replyId = replyId;
        this.postId = postId;
        this.replyBody = replyBody;
        this.repliedBy = repliedBy;
        this.repliedOn = repliedOn;
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
}
