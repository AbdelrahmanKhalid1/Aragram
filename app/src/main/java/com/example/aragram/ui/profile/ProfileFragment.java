package com.example.aragram.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.aragram.R;
import com.example.aragram.model.User;
import com.example.aragram.ui.followersprofile.FollowersActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    private static final String TAG = "MainActivity";
    private FirebaseFirestore db;

    Toolbar toolbar;
    TextView usernameToolbar;
    //FirebaseAuth mAuth= FirebaseAuth.getInstance();
    ImageView profilePicture;

    TextView noOfPosts;
    TextView noOfFollowers;
    TextView noOfFollowing;
    Button editButton;
    TextView bio;
    Button messageButton;
    ProfileViewModel profileViewModel;
    User finaluser;
    ProgressBar progressBar;
    TextView websiteUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_profile, container, false);

        profileViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(getContext())).get(ProfileViewModel.class);
        profileViewModel.getProfileData();
        initializeViews(v);

        profileViewModel.userMutableLiveData.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {

                finaluser = user;
                Log.d(TAG, "onChanged: " + finaluser);
                Log.d("haha", "onChanged: " + "good luck" + finaluser.getUsername());
                setViews();
                AppCompatActivity activity = ((AppCompatActivity) requireActivity());
                activity.getSupportActionBar().setTitle(finaluser.getUsername());
                activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
        noOfFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FollowersActivity.class);
                intent.putExtra("myProfile", finaluser.getUsername());
                intent.putExtra("flag", false);
                startActivity(intent);

            }
        });
        noOfFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FollowersActivity.class);
                intent.putExtra("myProfile", finaluser.getUsername());
                intent.putExtra("flag", true);
                startActivity(intent);

            }
        });


        return v;
    }

    private void setViews() {
        noOfPosts.setText(String.valueOf(finaluser.getPosts()));
        noOfFollowing.setText(String.valueOf(finaluser.getFollowing()));
        noOfFollowers.setText(String.valueOf(finaluser.getFollowers()));
        progressBar.setVisibility(View.GONE);
        bio.setText(finaluser.getBio());
        if (finaluser.getUserProfilePicture() != null) {
            Picasso.with(getContext()).load(finaluser.getUserProfilePicture()).into(profilePicture);

        }
        websiteUser.setText(finaluser.getWebsite());
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
    }

    private void initializeViews(View v) {
        bio = v.findViewById(R.id.textView4);
        toolbar = v.findViewById(R.id.toolbar);
//        usernameToolbar=v.findViewById(R.id.toolbar_text);
        progressBar = v.findViewById(R.id.profile_progressbar);
        noOfFollowers = v.findViewById(R.id.number_of_followers);
        noOfFollowing = v.findViewById(R.id.number_of_following);
        noOfPosts = v.findViewById(R.id.number_of_posts);
        editButton = v.findViewById(R.id.edit_profile_button);
        //messageButton=v.findViewById(R.id.message_button);
        progressBar.setVisibility(View.VISIBLE);
        profilePicture = v.findViewById(R.id.user_profile_picture);
        websiteUser = v.findViewById(R.id.website_text);
    }

}