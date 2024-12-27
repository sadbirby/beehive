package com.beehive.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts", indexes = @Index(name = "index_posted_by", columnList = "postedBy"))
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long postId;

    @NotNull
    private String postTitle;

    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    private String postBody;

    @NotNull
    private String postedBy;
    @NotNull
    private Date postedOn;
    @NotNull
    private Long numberOfLikes;
    @NotNull
    private Long numberOfReplies;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
            //          , orphanRemoval = true
    )
    private Set<PostLikeEntity> postLikedBy = new HashSet<>();

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
            //          , orphanRemoval = true
    )
    private Set<ReplyEntity> postReplies = new HashSet<>();

    public PostEntity() {
    }

    public PostEntity(
            Long postId,
            String postTitle,
            String postBody,
            String postedBy,
            Date postedOn,
            Long numberOfLikes,
            Long numberOfReplies,
            Set<PostLikeEntity> postLikedBy,
            Set<ReplyEntity> postReplies) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.postedBy = postedBy;
        this.postedOn = postedOn;
        this.numberOfLikes = numberOfLikes;
        this.numberOfReplies = numberOfReplies;
        this.postLikedBy = postLikedBy;
        this.postReplies = postReplies;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public Date getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
    }

    public Long getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(Long numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public Long getNumberOfReplies() {
        return numberOfReplies;
    }

    public void setNumberOfReplies(Long numberOfReplies) {
        this.numberOfReplies = numberOfReplies;
    }

    @JsonManagedReference
    public Set<PostLikeEntity> getPostLikedBy() {
        return postLikedBy;
    }

    public void setPostLikedBy(Set<PostLikeEntity> postLikedBy) {
        this.postLikedBy = postLikedBy;
    }

    @JsonManagedReference
    public Set<ReplyEntity> getPostReplies() {
        return postReplies;
    }

    public void setPostReplies(Set<ReplyEntity> postReplies) {
        this.postReplies = postReplies;
    }
}
