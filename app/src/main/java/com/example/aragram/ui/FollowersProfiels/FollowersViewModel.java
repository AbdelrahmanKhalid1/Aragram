package com.example.aragram.ui.FollowersProfiels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aragram.model.User;

import java.util.List;

public class FollowersViewModel extends ViewModel {
    MutableLiveData<List<User>> followersList;
    FollowersRepository followersRepository;
    MutableLiveData<List<User>> followingList;

    public FollowersViewModel() {
        followersRepository=new FollowersRepository();
        followersList=followersRepository.getFollowersList();
        followingList=followersRepository.getFollowingList();
    }

    public  void getFollowersProfiles(String username)
    {
        followersRepository.getFollowersProfiles(username);

    }
    public LiveData<List<User>> getFollowersList()
    {
        return followersList;
    }
    public LiveData<List<User>> getFollowingList()
    {
        return followingList;
    }
    public void getFollowingProfiles(String username)
    {
        followersRepository.getFollowingProfiles(username);
    }
}
