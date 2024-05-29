package com.beehive.dto;

import java.util.Date;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReplyDto {

    private String userId;
    private String replied;
    private Date dateReplied;

    public ReplyDto() {
    }

    public ReplyDto(String userId, String replied, Date dateReplied) {
        this.userId = userId;
        this.replied = replied;
        this.dateReplied = dateReplied;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReplied() {
        return replied;
    }

    public void setReplied(String replied) {
        this.replied = replied;
    }

    public Date getDateReplied() {
        return dateReplied;
    }

    public void setDateReplied(Date dateReplied) {
        this.dateReplied = dateReplied;
    }
}
