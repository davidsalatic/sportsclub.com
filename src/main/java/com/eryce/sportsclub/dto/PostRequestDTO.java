package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.CompetitionApplication;
import com.eryce.sportsclub.models.Post;

import java.time.LocalDateTime;

public class PostRequestDTO {

    private Integer id;
    private String title;
    private String text;
    private LocalDateTime dateTime;
    private AppUserRequestDTO appUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public AppUserRequestDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserRequestDTO appUser) {
        this.appUser = appUser;
    }

    public Post generatePost()
    {
        Post post = new Post();
        if(this.id!=null)
            post.setId(this.id);
        post.setTitle(this.title);
        post.setText(this.text);
        post.setAppUser(this.appUser.generateAppUser());
        post.setDateTime(this.dateTime);
        return post;
    }
}
