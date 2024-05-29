package com.beehive.request;

import com.beehive.dto.TweetsDto;

public class TweetRequest {

    private TweetsDto tweet;

    public TweetsDto getTweet() {
        return tweet;
    }

    public void setTweet(TweetsDto tweet) {
        this.tweet = tweet;
    }
}
