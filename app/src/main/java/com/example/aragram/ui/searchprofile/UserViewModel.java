package com.example.aragram.ui.searchprofile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aragram.repository.UserRepository;
import com.example.aragram.model.User;

public class UserViewModel extends ViewModel {
    LiveData<Boolean> mIsUpdating=new MutableLiveData<>();
    LiveData<Boolean> mIsUnFollowed=new MutableLiveData<>();
    LiveData<Boolean> mIsFollowed=new MutableLiveData<>();
    UserRepository userRepository;

    public UserViewModel() {
        userRepository= UserRepository.getInstance();
        mIsUpdating=userRepository.getmIsUpdating();
        mIsFollowed=userRepository.getmIsFollowed();
        mIsUnFollowed=userRepository.getmIsUnFollowed();

    }

    public LiveData<Boolean> getIsLoading()
    {
        return mIsUpdating;
    }
    public LiveData<Boolean> getIsFollowed()
    {
        return mIsFollowed;
    }
    public LiveData<Boolean> getIsUnFollowed()
    {
        return mIsUnFollowed;
    }
    public void followPerson(String name)
    {
        userRepository.followPerson(name);
    }
    public void checkPerson(final User user)
    {
        userRepository.checkPerson(user);

    }
    public void unFollowPerson(String name)
    {
        userRepository.unFollowPerson(name);

    }


}
