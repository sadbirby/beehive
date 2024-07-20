package com.beehive.entity.projection;

import java.util.Date;

public interface ReplyEntityProjection {
  Long getReplyId();

  String getReplyBody();

  String getRepliedBy();

  Date getRepliedOn();

  Long getNumberOfLikes();
}
