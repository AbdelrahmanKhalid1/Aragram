package com.example.aragram.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.aragram.R;
import com.example.aragram.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button createButton;
    Button loginButton;
    LoginViewModel loginViewModel;
    EditText username;
    EditText password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }

        createButton=findViewById(R.id.create_button);
        username=findViewById(R.id.username_login);
        progressBar=findViewById(R.id.login_progressbar);
        password=findViewById(R.id.password_login);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), RegisterActivty.class);
                startActivity(intent);

            }
        });
        loginButton=findViewById(R.id.button_login);
        loginViewModel= new ViewModelProvider(this, new LoginViewModelFactory(this)).get(LoginViewModel.class);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(username.getText().toString()+"@gmail.com",password.getText().toString());
            }
        });
        loginViewModel.loginStatus.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    progressBar.setVisibility(View.GONE);
                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "username/password invalid Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}