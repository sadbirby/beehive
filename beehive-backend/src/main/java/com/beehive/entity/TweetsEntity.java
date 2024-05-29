package com.beehive.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.beehive.dto.ReplyDto;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tweets")
public class TweetsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String tweetId;
    private String tweet;
    private String userTweetId;
    private Long likes;
    @ElementCollection
    private List<ReplyDto> reply = new ArrayList<>();
    private Date dateOfPost;

    public TweetsEntity() {
    }

    public TweetsEntity(String tweetId, String tweet, String userTweetId, Long likes, List<ReplyDto> reply, Date dateOfPost) {
        this.tweetId = tweetId;
        this.tweet = tweet;
        this.userTweetId = userTweetId;
        this.likes = likes;
        this.reply = reply;
        this.dateOfPost = dateOfPost;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
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

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
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
}
