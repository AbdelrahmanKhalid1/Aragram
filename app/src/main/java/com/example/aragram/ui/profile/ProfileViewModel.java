package com.example.aragram.ui.profile;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.aragram.repository.UserRepository;
import com.example.aragram.model.User;

public class ProfileViewModel extends ViewModel {
    LiveData<User> userMutableLiveData;
    UserRepository userRepository;

    Context context;



    public ProfileViewModel(Context context) {
        this.context = context;
        userRepository= UserRepository.getInstance();
        userMutableLiveData=userRepository.getUserMutableLiveData();

    }


    public void getProfileData()
    {
        Log.d("page1", "getProfileData: hiiiiii");
        userRepository.getProfileData();

    }


}
