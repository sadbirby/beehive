package com.beehive.web.request;

import com.beehive.domain.dto.ReplyDto;

public class ReplyRequest {

    private ReplyDto reply;

    public ReplyDto getReply() {
        return reply;
    }

    public void setReply(ReplyDto reply) {
        this.reply = reply;
    }
}
