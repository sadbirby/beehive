package com.beehive.response;

import com.beehive.dto.PostDto;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class PostResponse implements Serializable {

  @Serial private static final long serialVersionUID = -3452495145437447139L;

  private List<PostDto> posts;
  private String statusMessage;

  public List<PostDto> getPosts() {
    return posts;
  }

  public void setPosts(List<PostDto> posts) {
    this.posts = posts;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }
}
