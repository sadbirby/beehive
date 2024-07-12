package com.beehive.response;

import com.beehive.dto.ReplyDto;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class ReplyResponse implements Serializable {

  @Serial private static final long serialVersionUID = -3452495145437447139L;

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
