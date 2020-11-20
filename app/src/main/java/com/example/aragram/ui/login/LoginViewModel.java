package com.example.aragram.ui.login;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aragram.repository.UserRepository;
import com.example.aragram.model.User;

public class LoginViewModel extends ViewModel {
    Context context;
    UserRepository userRepository;
    LiveData<Boolean> loginStatus=new MutableLiveData<>();
    LiveData<Boolean> registerStatus=new MutableLiveData<>();
    public LoginViewModel(Context context)
    {
        this.context=context;
        userRepository= UserRepository.getInstance();
       registerStatus =userRepository.getCheckRegister();
       loginStatus=userRepository.getFlag();
    }

    public void login(String username, String password)
    {
        userRepository.login(username,password);
    }
    public  void register(User user)
    {
        userRepository.register(user);

    }

}
