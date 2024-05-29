package com.beehive.dto;

import java.util.List;

public class TweetsDto {

    private String tweet;
    private String userTweetId;
    private String tweetId;
    private Long like;
    private List<ReplyDto> reply;
    private String dateOfPost;
    private String timeOfPost;

    public TweetsDto() {
    }

    public TweetsDto(String tweet, String userTweetId, String tweetId, Long like, List<ReplyDto> reply, String dateOfPost, String timeOfPost) {
        this.tweet = tweet;
        this.userTweetId = userTweetId;
        this.tweetId = tweetId;
        this.like = like;
        this.reply = reply;
        this.dateOfPost = dateOfPost;
        this.timeOfPost = timeOfPost;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getUserTweetId() {
        return userTweetId;
    }

    public void setUserTweetId(String userTweetId) {
        this.userTweetId = userTweetId;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }

    public List<ReplyDto> getReply() {
        return reply;
    }

    public void setReply(List<ReplyDto> reply) {
        this.reply = reply;
    }

    public String getDateOfPost() {
        return dateOfPost;
    }

    public void setDateOfPost(String dateOfPost) {
        this.dateOfPost = dateOfPost;
    }

    public String getTimeOfPost() {
        return timeOfPost;
    }

    public void setTimeOfPost(String timeOfPost) {
        this.timeOfPost = timeOfPost;
    }
}
