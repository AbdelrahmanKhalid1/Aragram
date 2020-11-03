package com.example.aragram.ui.profile;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aragram.model.User;
import com.example.aragram.ui.FollowersProfiels.FollowersRepository;

import java.util.List;

public class ProfileViewModel extends ViewModel {
    MutableLiveData<User> userMutableLiveData;
    ProfileRepository profileRepository;

    Context context;



    public ProfileViewModel(Context context) {
        this.context = context;
        profileRepository=new ProfileRepository(context);
        userMutableLiveData=profileRepository.getUserMutableLiveData();

    }


    public void getProfileData()
    {
        Log.d("page1", "getProfileData: hiiiiii");
        profileRepository.getProfileData();

    }


}
