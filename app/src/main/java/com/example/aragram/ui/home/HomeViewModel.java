package com.example.aragram.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aragram.model.Post;
import com.example.aragram.repository.PostRepository;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {

    private PostRepository postRepository;
    private MutableLiveData<ArrayList<Post>> postList;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        postRepository = new PostRepository(application);
        postList = postRepository.getAllData();
    }

    public LiveData<ArrayList<Post>> getAllData()
    {
        return postList;
    }

    public void insertPost(Post post)
    {
        postRepository.insetNewPost(post);
    }

    public void updatePost(Post post)
    {
        postRepository.updatePostData(post);
    }
}
