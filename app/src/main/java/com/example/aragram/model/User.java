package com.example.aragram.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String password;
    private String age;
    private String gender;
    private int id;
    private int followers;
    private int following;
    private Bitmap profilePicture;

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    private String userProfilePicture;

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    private int posts;
    private String bio;
    private String website;

    public String getWebsite() {
        return website;
    }

    public User() {
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    List<User> followersUsers =new ArrayList<>();
    List<User> followingUsers =new ArrayList<>();

    public int getPosts() {
        return posts;
    }


    public User(String username, String password, String age, String gender, String bio,String website) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.bio=bio;
        this.website=website;
        followers=0;
        following=0;
        posts =0;

    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<User> getFollowersUsers() {
        return followersUsers;
    }

    public List<User> getFollowingUsers() {
        return followingUsers;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }
}
