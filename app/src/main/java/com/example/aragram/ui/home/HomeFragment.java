package com.example.aragram.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aragram.R;
import com.example.aragram.model.Post;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    //    private Post newPost;
    private RecyclerView postRecyclerView;
    private PostAdapter postAdapter;
    private ArrayList<Post> postArrayList;
//    private CommentFragment commentFragment;

    public HomeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        postRecyclerView = view.findViewById(R.id.recycler);
        postArrayList = new ArrayList<>();
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        initialViewModel();
        return view;
    }

    private void initialViewModel() {
        homeViewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {
                postArrayList = new ArrayList<>(posts);
                initRecyclerView();
                postAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerView() {
        postAdapter = new PostAdapter(postArrayList);
        RecyclerView.LayoutManager postLayoutManager = new LinearLayoutManager(getContext());
        postRecyclerView.setHasFixedSize(true);
        postRecyclerView.setAdapter(postAdapter);
        postRecyclerView.setLayoutManager(postLayoutManager);
//        final int[] updatePos = new int[1];
//        updatePos[0] = -1;
        postAdapter.setPostActionInt(new PostAdapter.PostAction() {

            @Override
            public void getLikeAction(int pos, ImageView imageView) {
                if (imageView.getTag().equals(R.drawable.ic_baseline_favorite_24)) {
                    postArrayList.get(pos).setLike(true);
                    imageView.setTag(R.drawable.ic_baseline_favorite_border_24);
//                    updatePos[0] = pos;
                    homeViewModel.updatePost(postArrayList.get(pos));
                }
            }

            @Override
            public void getSaveAction(int pos, ImageView imageView) {
                if (imageView.getTag().equals(R.drawable.ic_baseline_bookmark_241)) {
                    postArrayList.get(pos).setSave(true);
                    imageView.setTag(R.drawable.bookmark);
                    homeViewModel.updatePost(postArrayList.get(pos));
                }
            }

            @Override
            public void putComment(int pos) {
                if (pos != -1) {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, new CommentFragment(postArrayList.get(pos))).commit();
                }
            }
        });

    }
}