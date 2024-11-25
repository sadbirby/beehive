package com.beehive.entity;

import com.beehive.entity.composite.ReplyLikeId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@IdClass(ReplyLikeId.class)
@Table(name = "reply_likes")
public class ReplyLikeEntity {
    @Id
    private Long replyId;
    @Id
    private String replyLikedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replyId", insertable = false, updatable = false)
    private ReplyEntity reply;

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

    @JsonBackReference
    public ReplyEntity getReply() {
        return reply;
    }

    public void setReply(ReplyEntity reply) {
        this.reply = reply;
    }
}
