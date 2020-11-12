package com.example.aragram.ui.profile;

import android.content.ContentResolver;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aragram.Repository.UserRepository;
import com.example.aragram.model.User;

public class EditProfileViewModel extends ViewModel {
    LiveData<Boolean> changeStatus;
    LiveData<Boolean> uploadPhotoStatus;
    UserRepository userRepository;

    public EditProfileViewModel() {
        userRepository=UserRepository.getInstance();
        changeStatus=userRepository.getChangeStatus();
        uploadPhotoStatus=userRepository.getUploadPhotoStatus();

    }
    public LiveData<Boolean> getPhotoStatus()
    {
        return uploadPhotoStatus;
    }
    public LiveData<Boolean> getChangeStatus()
    {
        return changeStatus;
    }
    public void makeChange(User user)
    {
        userRepository.makeChange(user);

    }
    public void uploadFile(String s, Uri uri)
    {
        userRepository.uploadFile(s,uri);
    }
}
