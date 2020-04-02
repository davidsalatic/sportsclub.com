package com.eryce.sportsclub.dto;

import com.eryce.sportsclub.models.Comment;

import java.time.LocalDateTime;

public class CommentRequestDTO {

    private Integer id;
    private String text;
    private LocalDateTime dateTime;
    private PostRequestDTO post;
    private AppUserRequestDTO appUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public PostRequestDTO getPost() {
        return post;
    }

    public void setPost(PostRequestDTO post) {
        this.post = post;
    }

    public AppUserRequestDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserRequestDTO appUser) {
        this.appUser = appUser;
    }

    public Comment generateComment()
    {
        Comment comment = new Comment();
        if(this.id!=null)
            comment.setId(this.id);
        comment.setText(this.text);
        comment.setDateTime(this.dateTime);
        comment.setAppUser(appUser.generateAppUser());
        comment.setPost(post.generatePost());
        return comment;
    }
}
