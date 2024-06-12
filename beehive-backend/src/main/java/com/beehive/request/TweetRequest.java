package com.beehive.request;

import java.io.Serial;
import java.io.Serializable;

import com.beehive.dto.TweetsDto;

public class TweetRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8300957195016445421L;

    private TweetsDto tweet;

    public TweetsDto getTweet() {
        return tweet;
    }

    public void setTweet(TweetsDto tweet) {
        this.tweet = tweet;
    }
}
