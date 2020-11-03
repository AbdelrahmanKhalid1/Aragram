package com.example.aragram.ui.profile;

import android.content.ContentResolver;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aragram.model.User;

public class EditProfileViewModel extends ViewModel {
    MutableLiveData<Boolean> changeStatus;
    MutableLiveData<Boolean> uploadPhotoStatus;
    EditProfileRepository editProfileRepository;

    public EditProfileViewModel() {
        editProfileRepository=new EditProfileRepository();
        changeStatus=editProfileRepository.getChangeStatus();
        uploadPhotoStatus=editProfileRepository.getUploadPhotoStatus();

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
        editProfileRepository.makeChange(user);

    }
    public void uploadFile(String s, Uri uri)
    {
        editProfileRepository.uploadFile(s,uri);
    }
}
