package com.example.aragram.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aragram.R;
import com.example.aragram.model.User;

public class RegisterActivty extends AppCompatActivity {
    EditText username_text;
    EditText password_text;
    EditText gender_text;
    EditText age_text;
    Button register_button;
    private LoginViewModel loginViewModel;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username_text=findViewById(R.id.username_register);
        password_text=findViewById(R.id.password_register);
        gender_text=findViewById(R.id.gender_register);
        age_text=findViewById(R.id.age_register);
        register_button=findViewById(R.id.register_button);
        loginViewModel= new ViewModelProvider(this, new LoginViewModelFactory(this)).get(LoginViewModel.class);
        progressBar=findViewById(R.id.register_progressbar);
        loginViewModel.registerStatus.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivty.this, "i am hereeeeeeee", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(RegisterActivty.this, "a7oo", Toast.LENGTH_SHORT).show();


                }
            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(username_text.getText().toString().length()<2)
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivty.this, "Username is too small", Toast.LENGTH_SHORT).show();

                }
                else if(password_text.getText().toString().length()<6)
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivty.this, "Password must be bigger than 6 digits", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginViewModel.register(new User(username_text.getText().toString(),
                            password_text.getText().toString(),age_text.getText().toString(),
                            gender_text.getText().toString()," "," "));
                }


            }
        });
    }
}