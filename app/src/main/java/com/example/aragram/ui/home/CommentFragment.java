package com.example.aragram.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aragram.ui.MainActivity;
import com.example.aragram.R;
import com.example.aragram.model.Post;
import com.example.aragram.model.PostComment;
import com.example.aragram.model.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentFragment extends Fragment {
    private CircleImageView mProfilePostImageView;
    private TextView mProfilePostName;
    private TextView mPostDescription;
    private TextView mPostTime;
    private RecyclerView mCommentsRecy;
    private TextInputLayout mAddComment;
    private TextView mCommentPost;

    private Post currentPost;
    private List<PostComment> commentList;
    private RecyclerView.LayoutManager commentLayout;
    private CommentAdapter commentAdapter;
    private UpdateCommentList listener;

    public CommentFragment(Post post) {
        this.currentPost = post;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comments_fragment,container,false);

        mProfilePostImageView = view.findViewById(R.id.comments_fragment_profile_image);
        mProfilePostName = view.findViewById(R.id.comments_fragment_profile_name);
        mPostDescription = view.findViewById(R.id.comments_fragment_comments_description);
        mPostTime = view.findViewById(R.id.comments_fragment_post_time);
        mCommentsRecy = view.findViewById(R.id.comments_fragment_recyclerview);
        mAddComment = view.findViewById(R.id.comments_fragment_add_comments);
        mCommentPost = view.findViewById(R.id.comments_fragment_post_comment);

        mProfilePostName.setText(currentPost.getUserProfile().getUsername());
        mPostDescription.setText(currentPost.getDescription());
        mPostTime.setText(String.valueOf(currentPost.getTime()));

        commentList = currentPost.getCommentList();
        final User postUser = currentPost.getUserProfile();
        initialCommentRecy();
        mCommentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commant = mAddComment.getEditText().getText().toString().trim();
                commentList.add(new PostComment(currentPost, MainActivity.loginUser,commant,
                        "0",0,new ArrayList<PostComment>(),false));
                currentPost.setCommentList(commentList);
                listener.updateCommentList(currentPost);
                commentAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private void initialCommentRecy()
    {
        commentAdapter = new CommentAdapter((ArrayList<PostComment>) commentList);
        mCommentsRecy.setAdapter(commentAdapter);
        mCommentsRecy.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }

    public interface UpdateCommentList
    {
        public void updateCommentList(Post post);
    }

}
