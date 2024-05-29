package com.beehive.service;

import com.beehive.request.TweetRequest;
import com.beehive.response.TweetResponse;

public interface TweetsService {

    TweetResponse getAllTweets();

    TweetResponse getAllTweetsByUserName(String userName);

    TweetResponse addTweet(TweetRequest request, String userName);

    TweetResponse deleteTweet(String userName, String tweetId);

    TweetResponse replyToTweet(TweetRequest request);

    TweetResponse likeATweet(TweetRequest request);

    TweetResponse unlikeATweet(TweetRequest request);

    TweetResponse updateTweet(TweetRequest request);
}
