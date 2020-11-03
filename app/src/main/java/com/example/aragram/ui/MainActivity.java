package com.example.aragram.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aragram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        Toast.makeText(this, "lol"+navView.getSelectedItemId(), Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, "username"+mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
    }



}