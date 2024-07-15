package com.beehive.response;

import com.beehive.dto.ReplyDto;
import java.util.List;

public class ReplyResponse {

  private List<ReplyDto> replies;
  private String statusMessage;

  public List<ReplyDto> getReplies() {
    return replies;
  }

  public void setReplies(List<ReplyDto> replies) {
    this.replies = replies;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }
}
