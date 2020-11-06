package com.example.aragram.ui.searchprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aragram.R;
import com.example.aragram.model.User;
import com.example.aragram.ui.FollowersProfiels.FollowersActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class SearchProfile extends AppCompatActivity {
    User user;
    TextView numberOfPosts;
    ImageView profilePictureImage;
    TextView followerView;
    TextView followingView;
    Button followButton;
    Button messageButton;
    TextView toolbarText;
    SearchProfileViewModel searchProfileViewModel;
    Boolean checkUnFollow=false;
    TextView bioUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profile);
       user= (User) getIntent().getExtras().getSerializable("user");
       initUI();
       displayUserData(user);
       searchProfileViewModel.checkPerson(user);
       searchProfileViewModel.getIsFollowed().observe(this, new Observer<Boolean>() {
           @SuppressLint("ResourceAsColor")
           @Override
           public void onChanged(Boolean aBoolean) {
               if(aBoolean)
               {
                   checkUnFollow=true;
                   followButton.setText("Followed");
                   followButton.setBackgroundResource(R.drawable.edit_shape);
               }


           }
       });

       searchProfileViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
           @SuppressLint("ResourceAsColor")
           @Override
           public void onChanged(Boolean aBoolean) {
               if(aBoolean)
               {
                   followerView.setText(String.valueOf(user.getFollowers()+1));
                   followButton.setText("Followed");
                   followButton.setBackgroundResource(R.drawable.edit_shape);
                   checkUnFollow=true;

               }
               else
               {
                   Toast.makeText(SearchProfile.this, "lol No!!!!", Toast.LENGTH_SHORT).show();
               }
           }
       });
       searchProfileViewModel.getIsUnFollowed().observe(this, new Observer<Boolean>() {
           @SuppressLint("ResourceAsColor")
           @Override
           public void onChanged(Boolean aBoolean) {
               if(aBoolean)
               {
                   followerView.setText(String.valueOf(user.getFollowers()));
                   followButton.setText("Follow");
                   followButton.setBackgroundResource(R.drawable.edit_shape3);
               }

           }
       });


    }

    private void initUI() {
        numberOfPosts=findViewById(R.id.number_of_posts);
        followingView=findViewById(R.id.number_of_following);
        followerView=findViewById(R.id.number_of_followers);
        followButton=findViewById(R.id.follow_button);
        messageButton=findViewById(R.id.message_button);
        toolbarText=findViewById(R.id.toolbar_text);
        searchProfileViewModel= new ViewModelProvider(this).get(SearchProfileViewModel.class);
        profilePictureImage=findViewById(R.id.user_profile_picture);
        bioUser=findViewById(R.id.textView4);

    }

    private void displayUserData(User user) {
        numberOfPosts.setText(String.valueOf(user.getPosts()));
        followerView.setText(String.valueOf(user.getFollowers()));
        followingView.setText(String.valueOf(user.getFollowing()));
        toolbarText.setText(user.getUsername());
        if(user.getUserProfilePicture()!=null)
        {
            Picasso.with(this).load(user.getUserProfilePicture()).into(profilePictureImage);
        }
        bioUser.setText(user.getBio());


    }


    public void followPerson(View view) {
        if(!checkUnFollow)
        {
            searchProfileViewModel.followPerson(user.getUsername());
        }
        else
        {
            searchProfileViewModel.unFollowPerson(user.getUsername());
            checkUnFollow=false;
        }

    }

    public void showFollowers(View view) {
        Intent intent =new Intent(this, FollowersActivity.class);
        intent.putExtra("myProfile",user.getUsername());
        startActivity(intent);
    }

    public void showFollowing(View view) {
        Intent intent =new Intent(this, FollowersActivity.class);
        intent.putExtra("myProfile",user.getUsername());
        intent.putExtra("flag",true);
        startActivity(intent);
    }
}