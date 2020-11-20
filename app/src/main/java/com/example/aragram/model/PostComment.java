package com.example.aragram.model;

import java.util.ArrayList;

public class PostComment {
    private String comment;
    private String time;
    private int likes;
    private boolean fav;
    private ArrayList<PostComment> reply;
    private Post post;
    private User user;
    public PostComment() {
    }

    public PostComment(Post post, User user , String comment, String time, int likes, ArrayList<PostComment> reply, boolean fav) {
        this.user = user;
        this.post = post;
        this.comment = comment;
        this.time = time;
        this.likes = likes;
        this.reply = reply;
        this.fav = fav;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setReply(ArrayList<PostComment> reply) {
        this.reply = reply;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public String getTime() {
        return time;
    }

    public int getLikes() {
        return likes;
    }

    public ArrayList<PostComment> getReply() {
        return reply;
    }
}
