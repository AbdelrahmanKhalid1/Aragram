package com.example.aragram.ui.FollowersProfiels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.aragram.R;
import com.example.aragram.model.User;
import com.example.aragram.ui.searchprofile.SearchProfile;
import com.example.aragram.ui.searchprofile.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class FollowersActivity extends AppCompatActivity {
    RecyclerView followersPerson;
    SearchAdapter searchAdapter;
    String name;
    FollowersViewModel followersViewModel;
    List<User> finalUsers;
    Boolean checkFollowing=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        name=getIntent().getStringExtra("myProfile");
        Bundle bundle=getIntent().getExtras();
        checkFollowing=bundle.getBoolean("flag");
        initRecyclerView();
        followersViewModel= new ViewModelProvider(this).get(FollowersViewModel.class);

        if(!checkFollowing)
        {
            Log.d("dodo", "onCreate: hereee");
            followersViewModel.getFollowersProfiles(name);
        }
        else
        {
            Log.d("dodo", "onCreate: hereee2");
            followersViewModel.getFollowingProfiles(name);
        }

        followersViewModel.getFollowersList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
               // Toast.makeText(FollowersActivity.this, "kam"+users.size()+" "+users.get(0).getUsername(), Toast.LENGTH_SHORT).show();
                finalUsers.clear();
                finalUsers.addAll(users);
                searchAdapter.notifyDataSetChanged();
            }
        });
        searchAdapter.setmListner(new SearchAdapter.OnItemClickListner() {
            @Override
            public void onClick(int position) {
                Intent intent =new Intent(getBaseContext(), SearchProfile.class);
                intent.putExtra("user",finalUsers.get(position));
                startActivity(intent);
            }
        });
        followersViewModel.getFollowingList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                finalUsers.clear();
                finalUsers.addAll(users);
                searchAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerView() {
        finalUsers=new ArrayList<>();
        followersPerson=findViewById(R.id.followers_recyclerview);
        followersPerson.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter=new SearchAdapter(finalUsers,getApplicationContext());
        followersPerson.setAdapter(searchAdapter);
    }
}