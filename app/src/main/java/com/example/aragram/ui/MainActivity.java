package com.example.aragram.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.aragram.R;
import com.example.aragram.model.Post;
import com.example.aragram.model.User;
import com.example.aragram.ui.gallery.GalleryFragment;
import com.example.aragram.ui.home.HomeFragment;
import com.example.aragram.ui.home.PostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements GalleryFragment.FragmentGalleryMain, PostFragment.FragmentPostMain {

    private FirebaseAuth mAuth;
    public static User loginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginUser = new User("rafeek");
        BottomNavigationView navView = findViewById(R.id.nav_view);
//        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new GalleryFragment()).commit();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }
    @Override
    protected void onStart() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("admin@gmail.com", "admin123");
        super.onStart();
    }

    @Override
    public void getSelectedImage(String imageUri) {
        PostFragment postFragment = new PostFragment();
        postFragment.setImageUri(imageUri);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,postFragment).commit();
    }

    @Override
    public void setPost(Post post) {
        System.out.println("ppppppppppppppppppppppppppp "+post.getDescription() + "  "+post.getUserProfile().getUsername());
        HomeFragment homeFragment = new HomeFragment();
//        homeFragment.addNewPost(post);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,homeFragment).commit();
    }
}