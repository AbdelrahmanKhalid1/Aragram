package com.example.aragram.ui.searchprofile.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aragram.R;
import com.example.aragram.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter  extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private OnItemClickListner mListner;
    private List<User> users;
    private Context context;
    public interface OnItemClickListner
    {
        void onClick(int position);
    }

    public SearchAdapter(List<User> users, Context context) {
        this.users = users;
        this.context=context;

    }


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile,parent,false),mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.profileName.setText(users.get(position).getUsername());
        if(users.get(position).getUserProfilePicture()!=null)
        {
            Log.d("what?", "onBindViewHolder: "+users.get(position).getUsername());
            Picasso.with(context).load(users.get(position).getUserProfilePicture()).into(holder.profileImage);
        }
        else
        {
            Picasso.with(context).load(R.mipmap.profile_default).into(holder.profileImage);

        }


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setmListner(OnItemClickListner mListner) {
        this.mListner = mListner;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder
    {
        ImageView profileImage;
        TextView profileName;
        public SearchViewHolder(@NonNull View itemView, final OnItemClickListner listner) {
            super(itemView);
            profileName=itemView.findViewById(R.id.profile_name);
            profileImage=itemView.findViewById(R.id.profile_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listner.onClick(position);
                        }
                    }
                }
            });
        }
    }
}
