package com.example.aragram.ui.searchprofile.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.aragram.repository.UserRepository;
import com.example.aragram.model.User;

import java.util.List;

public class SearchViewModel extends ViewModel {
    LiveData<List<User>> usersResult;
    LiveData<List<User>> filterdUsers;
    UserRepository userRepository;

    public SearchViewModel() {
        userRepository= UserRepository.getInstance();
        usersResult=userRepository.getUsersResult();
        filterdUsers=userRepository.getFilterdUsers();
    }

    public void getProfiles()
    {
        userRepository.getProfiles();
    }
    public void getFilterProfiles(String s)
    {
        userRepository.getFilterProfiles(s);
    }

}
