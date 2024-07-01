package com.beehive.entity.projection;

import java.util.Date;

public interface PostEntityProjection {
    Long getPostId();
    String getPostTitle();
    String getPostBody();
    String getPostedBy();
    Date getPostedOn();
}
