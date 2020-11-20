package com.example.aragram.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aragram.R;
import com.example.aragram.model.Post;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.HomeViewHolder> {

    public static final String TAG = "HomeAdapter";
    public List<Post> postAttributeList;
    public boolean imageClicked;
    public PostAction mLisnter;

    public PostAdapter(List<Post> postAttributeList) {
        this.postAttributeList = postAttributeList;
    }

    public void setPostActionInt(PostAction postAction) {
        this.mLisnter = postAction;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        HomeViewHolder homeViewHolder = new HomeViewHolder(view);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return postAttributeList.size();
    }

    public interface PostAction {
        public void getLikeAction(int pos, ImageView imageView);

        public void getSaveAction(int pos, ImageView imageView);

        public void putComment(int pos);
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private TextView mProfileName;
        private CircleImageView mProfilePhoto;
        private ImageView mPostPhoto;
        private ImageButton mLikePost;
        private ImageButton mSavePost;
        private TextView mLikes;
        private TextView mComments;
        private TextView mPostTime;
        private TextView mDescription;
        private ImageView mFavAnimation;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageClicked = false;
            mProfilePhoto = itemView.findViewById(R.id.cardview_profile_image);
            mProfileName = itemView.findViewById(R.id.cardview_profile_Name);
            mPostPhoto = itemView.findViewById(R.id.cardview_post);
            mLikePost = itemView.findViewById(R.id.cardview_fav);
            mSavePost = itemView.findViewById(R.id.cardview_bookmarket);
            mLikes = itemView.findViewById(R.id.cardview_likes_txt);
            mComments = itemView.findViewById(R.id.cardview_comments_txt);
            mPostTime = itemView.findViewById(R.id.cardview_postedtime);
            mDescription = itemView.findViewById(R.id.cardview_description_txt);
            mFavAnimation = itemView.findViewById(R.id.cardView_relative_of_fav);
            mLikePost.setTag(R.drawable.ic_baseline_favorite_border_24);
            mSavePost.setTag(R.drawable.bookmark);

            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    imageClicked = false;
                }
            };

            mPostPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //double click of photo
                    if (imageClicked) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mFavAnimation.setVisibility(View.GONE);
                            }
                        }, 1000);
                        mLisnter.getLikeAction(getAdapterPosition(), mLikePost);
                        mFavAnimation.setVisibility(View.VISIBLE);
                        mLikePost.setImageResource(R.drawable.ic_baseline_favorite_24);
                        imageClicked = false;
                        handler.removeCallbacks(runnable);


                    } else {
                        imageClicked = true;
                        handler.postDelayed(runnable, 500);
                    }
                }
            });

            mLikePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //fav click icon

                    if (mLikePost.getTag().equals(R.drawable.ic_baseline_favorite_border_24)) {
                        mLikePost.setImageResource(R.drawable.ic_baseline_favorite_24);
                        mLikePost.setTag(R.drawable.ic_baseline_favorite_24);
                        mLisnter.getLikeAction(getAdapterPosition(), mLikePost);
                    } else {
                        mLikePost.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        mLikePost.setTag(R.drawable.ic_baseline_favorite_border_24);
                    }
                }
            });

            mSavePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //save icon click
                    if (mSavePost.getTag().equals(R.drawable.bookmark)) {
                        mSavePost.setTag(R.drawable.ic_baseline_bookmark_241);
                        mSavePost.setImageResource(R.drawable.ic_baseline_bookmark_241);
                        mLisnter.getSaveAction(getAdapterPosition(), mSavePost);
                    } else {
                        mSavePost.setTag(R.drawable.bookmark);
                        mSavePost.setImageResource(R.drawable.bookmark);
                    }
                }
            });

            mComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mLisnter.putComment(getAdapterPosition());
                }
            });
        }

        public void setData(int pos) {
            Post item = postAttributeList.get(pos);
            Bitmap bitmap = BitmapFactory.decodeFile(item.getPostImage());
            mPostPhoto.setImageBitmap(bitmap);
            mProfileName.setText(item.getUserProfile().getUsername());
            mPostTime.setText(String.valueOf(item.getTime()));
            mLikes.setText(String.valueOf(item.getLikeList().size()) + "likes");
            mComments.setText("View all " + String.valueOf(item.getCommentList().size()) + "comments");
            mDescription.setText(item.getDescription());
            if (item.isLike()) {
                mLikePost.setImageResource(R.drawable.ic_baseline_favorite_24);
            } else {
                mLikePost.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }
            if (item.isSave()) {
                mSavePost.setImageResource(R.drawable.ic_baseline_bookmark_241);
            } else {
                mSavePost.setImageResource(R.drawable.bookmark);
            }
        }
    }
}
