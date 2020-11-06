package com.example.aragram.model;

import com.google.firebase.database.Exclude;

import java.util.List;

public class Post {
    private User userProfile;
    private String postImage;
    private boolean like;
    private boolean save;
    private List<User> likeList;
    private List<PostComment> commentList;
    private int time;
    private String description;

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    @Exclude
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Exclude
    private String documentId;

    public Post() {
    }

    public Post(User userProfile, String postImage, boolean like, boolean save,
                List<User> likeList, List<PostComment> commentList, int time, String description) {
        this.userProfile = userProfile;
        this.postImage = postImage;
        this.like = like;
        this.save = save;
        this.likeList = likeList;
        this.commentList = commentList;
        this.time = time;
        this.description = description;
    }

    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public void setLikeList(List<User> likeList) {
        this.likeList = likeList;
    }

    public void setCommentList(List<PostComment> commentList) {
        this.commentList = commentList;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUserProfile() {
        return userProfile;
    }

    public String getPostImage() {
        return postImage;
    }

    public boolean isLike() {
        return like;
    }

    public boolean isSave() {
        return save;
    }

    public List<User> getLikeList() {
        return likeList;
    }

    public List<PostComment> getCommentList() {
        return commentList;
    }

    public int getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }
}
