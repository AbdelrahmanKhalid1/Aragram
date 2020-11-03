package com.example.aragram.ui.login;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aragram.model.User;
import com.example.aragram.ui.login.LoginRepository;

public class LoginViewModel extends ViewModel {
    Context context;
     LoginRepository loginRepository;
    MutableLiveData<Boolean> loginStatus=new MutableLiveData<>();
    MutableLiveData<Boolean> registerStatus=new MutableLiveData<>();
    public LoginViewModel(Context context)
    {
        this.context=context;
        loginRepository= new LoginRepository(context);
       registerStatus =loginRepository.getCheckRegister();
       loginStatus=loginRepository.getFlag();
    }

    public void login(String username, String password)
    {
        loginRepository.login(username,password);
    }
    public  void register(User user)
    {
         loginRepository.register(user);
        //new RegisterAsyncTask(loginRepository).execute(profile);
    }
   /* private static class RegisterAsyncTask extends AsyncTask<Profile,Void,Void>
    {
        private LoginRepository loginRepository;

        public RegisterAsyncTask(LoginRepository loginRepository) {
            this.loginRepository=loginRepository;
        }


        @Override
        protected Void doInBackground(Profile... profiles) {
            loginRepository.register(profiles[0]);
            return null;
        }
    }

    */


}
