package com.example.aragram.ui.searchprofile.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aragram.model.User;

import java.util.List;

public class SearchFragmentViewModel extends ViewModel {
    MutableLiveData<List<User>> usersResult;
    MutableLiveData<List<User>> filterdUsers;
    SearchFragmentRepository searchFragmentRepository;

    public SearchFragmentViewModel() {
        searchFragmentRepository= new SearchFragmentRepository();
        usersResult=searchFragmentRepository.getUsersResult();
        filterdUsers=searchFragmentRepository.getFilterdUsers();
    }

    public void getProfiles()
    {
     searchFragmentRepository.getProfiles();
    }
    public void getFilterProfiles(String s)
    {
     searchFragmentRepository.getFilterProfiles(s);
    }
}
