package com.example.aragram.ui.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aragram.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleyrAdapter extends RecyclerView.Adapter<GalleyrAdapter.GalleryViewHolder> {

    public ArrayList<String> photoArrayList;
    public Context context;
    public getSelectedImage mSelectedImageInteface;
    public GalleyrAdapter(ArrayList<String> photoArrayList, Context context) {
        this.photoArrayList = photoArrayList;
        this.context = context;
    }

    public void setmSelectedImageInteface(getSelectedImage imageInteface)
    {
        this.mSelectedImageInteface = imageInteface;
    }


    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_cardview, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        holder.setData(photoArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return photoArrayList.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mSelectedImageInteface != null)
                    {
                        int pos = getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION)
                        {
                            mSelectedImageInteface.getSelectedItemPosition(pos);
                        }
                    }
                }
            });
        }

        private void setData(String path) {
//            Picasso.with(itemView.getContext())
//                    .load(path)
//                    .error(R.drawable.camera)
//                    .into(mImageView);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            mImageView.setImageBitmap(bitmap);
        }
    }

    public interface getSelectedImage
    {
        void getSelectedItemPosition(int pos);
    }


}
