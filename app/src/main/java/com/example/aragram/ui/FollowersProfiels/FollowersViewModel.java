package com.example.aragram.ui.FollowersProfiels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aragram.Repository.UserRepository;
import com.example.aragram.model.User;

import java.util.List;

public class FollowersViewModel extends ViewModel {
    LiveData<List<User>> followersList;
    UserRepository userRepository;
    LiveData<List<User>> followingList;

    public FollowersViewModel() {
        userRepository=UserRepository.getInstance();
        followersList=userRepository.getFollowersList();
        followingList=userRepository.getFollowingList();
    }

    public  void getFollowersProfiles(String username)
    {
        userRepository.getFollowersProfiles(username);

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
        userRepository.getFollowingProfiles(username);
    }
}
