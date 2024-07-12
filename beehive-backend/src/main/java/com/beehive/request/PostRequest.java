package com.beehive.request;

import com.beehive.dto.PostDto;
import java.io.Serial;
import java.io.Serializable;

public class PostRequest implements Serializable {

  @Serial private static final long serialVersionUID = -8300957195016445421L;

  private PostDto post;

  public PostDto getPost() {
    return post;
  }

  public void setPost(PostDto post) {
    this.post = post;
  }
}
