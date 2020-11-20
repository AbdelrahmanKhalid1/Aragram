package com.example.aragram.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aragram.R;
import com.example.aragram.model.PostComment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    public ArrayList<PostComment> commentArrayList;

    public CommentAdapter(ArrayList<PostComment> commentArrayList) {
        this.commentArrayList = commentArrayList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_cardview,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder
    {
        private CircleImageView mProfileImage;
        private TextView mProfileName;
        private TextView mCommentDescription;
        private TextView mCommentTime;
        private TextView mCommentLike;
        private TextView mCommentReply;
        private ImageButton mCommentFav;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            mProfileImage = itemView.findViewById(R.id.comments_cardview_profile_image);
            mProfileName = itemView.findViewById(R.id.comments_cardview_profile_name);
            mCommentDescription = itemView.findViewById(R.id.comments_cardview_comment);
            mCommentTime = itemView.findViewById(R.id.comments_cardview_comment_time);
            mCommentLike =itemView.findViewById(R.id.comments_cardview_comment_number_like);
            mCommentReply = itemView.findViewById(R.id.comments_cardview_comment_reply);
            mCommentFav = itemView.findViewById(R.id.comments_cardview_comment_like_img);
        }

        public void setData(int pos)
        {
            PostComment item = commentArrayList.get(pos);
            //mProfileImage.setImageResource(item.getProfileIamge());
           //mProfileName.setText(item.getUser().getName());
            mCommentDescription.setText(item.getComment());
            mCommentTime.setText(item.getTime());
            mCommentLike.setText(String.valueOf(item.getLikes())+" likes");
            //mCommentReply.setText(item.getReply().size());
            if(item.isFav())
            {
                mCommentFav.setImageResource(R.drawable.comment_like);
            }
        }
    }
}
