package com.example.aragram.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aragram.ui.MainActivity;
import com.example.aragram.R;
import com.example.aragram.model.Post;
import com.example.aragram.model.PostComment;
import com.example.aragram.model.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class PostFragment extends Fragment {

    private String imageUri;
    private Post newPost;
    private FragmentPostMain mListener;
    private String caption;
    private ImageView mImageView;
    private TextInputLayout mCaption;
    private Button mShare;
    public PostFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        mImageView = view.findViewById(R.id.fragment_post_imageContainer);
        mCaption = view.findViewById(R.id.fragment_post_caption);
        mShare = view.findViewById(R.id.fragment_post_share);

        Bitmap bitmap = BitmapFactory.decodeFile(imageUri);
        mImageView.setImageBitmap(bitmap);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caption = mCaption.getEditText().getText().toString().trim();
                ArrayList<User> likeList = new ArrayList<>();
                ArrayList<PostComment> commentList = new ArrayList<>();
                commentList.add(new PostComment());
                likeList.add(new User("fellly"));
                newPost = new Post(MainActivity.loginUser,imageUri,false,false,likeList,
                        commentList,00,caption);
                mListener.setPost(newPost);
            }
        });
        return view;
    }

    public void setImageUri(String uri)
    {
        imageUri = uri;
    }

    public interface FragmentPostMain
    {
        public void setPost(Post post);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentPostMain)
        {
            mListener = (FragmentPostMain) context;
        }
        else
        {
            throw new RuntimeException(context.toString()+"must implement Fragment Post Main");
        }
    }


}
