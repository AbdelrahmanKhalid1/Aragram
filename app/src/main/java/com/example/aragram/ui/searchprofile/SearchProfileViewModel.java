package com.example.aragram.ui.searchprofile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aragram.model.User;

public class SearchProfileViewModel extends ViewModel {
    MutableLiveData<Boolean> mIsUpdating=new MutableLiveData<>();
    MutableLiveData<Boolean> mIsUnFollowed=new MutableLiveData<>();
    MutableLiveData<Boolean> mIsFollowed=new MutableLiveData<>();
    SearchProfileRepository searchProfileRepository;

    public SearchProfileViewModel() {
        searchProfileRepository=new SearchProfileRepository();
        mIsUpdating=searchProfileRepository.getmIsUpdating();
        mIsFollowed=searchProfileRepository.getmIsFollowed();
        mIsUnFollowed=searchProfileRepository.getmIsUnFollowed();

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
        searchProfileRepository.followPerson(name);
    }
    public void checkPerson(final User user)
    {
        searchProfileRepository.checkPerson(user);

    }
    public void unFollowPerson(String name)
    {
        searchProfileRepository.unFollowPerson(name);

    }


}
