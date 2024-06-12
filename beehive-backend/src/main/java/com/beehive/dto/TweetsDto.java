package com.beehive.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class TweetsDto {

    private String tweet;
    private String userTweetId;
    private String tweetId;
    private Long like;
    private List<ReplyDto> reply;
    private Set<String> likedBy;
    private Date dateOfPost;

    public TweetsDto() {
    }

    public TweetsDto(String tweet, String userTweetId, String tweetId, Long like, List<ReplyDto> reply, Set<String> likedBy, Date dateOfPost) {
        this.tweet = tweet;
        this.userTweetId = userTweetId;
        this.tweetId = tweetId;
        this.like = like;
        this.reply = reply;
        this.likedBy = likedBy;
        this.dateOfPost = dateOfPost;
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

    public Date getDateOfPost() {
        return dateOfPost;
    }

    public void setDateOfPost(Date dateOfPost) {
        this.dateOfPost = dateOfPost;
    }

    public Set<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<String> likedBy) {
        this.likedBy = likedBy;
    }
}
