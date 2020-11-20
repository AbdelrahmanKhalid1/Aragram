package com.example.aragram.ui.gallery;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aragram.R;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private static final int MY_READ_PERMISSION_CODE = 101;
    private ImageView mSelectedPhoto;
    private RecyclerView mPhotoRec;
    private GalleyrAdapter galleryAdapter;
    private ArrayList<String> imageList;
    private FragmentGalleryMain mListener;
    private int selectedPhoto = -1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mSelectedPhoto = view.findViewById(R.id.collapsing_toolbar_imageview);
        mPhotoRec = view.findViewById(R.id.gallery_fragment_recyclerview);
        Toolbar toolbar = view.findViewById(R.id.collapsing_toolbar_toolbar);
        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbar);
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
        } else {
            loadImage();
        }
        return view;
    }

    private void loadImage() {
        mPhotoRec.setHasFixedSize(true);
        mPhotoRec.setLayoutManager(new GridLayoutManager(getContext(), 2));
        imageList = ImageGallery.listOfImage(requireContext());
        galleryAdapter = new GalleyrAdapter(imageList, getContext());
        mPhotoRec.setAdapter(galleryAdapter);
        galleryAdapter.setmSelectedImageInteface(new GalleyrAdapter.getSelectedImage() {
            @Override
            public void getSelectedItemPosition(int pos) {
                selectedPhoto = pos;
                Bitmap bitmap = BitmapFactory.decodeFile(imageList.get(pos));
                mSelectedPhoto.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_READ_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "granted", Toast.LENGTH_SHORT).show();
                loadImage();
            } else {
                Toast.makeText(getContext(), "denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_selected_item, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.selected_item) {
            Toast.makeText(getContext(),"hellllllo "+selectedPhoto,Toast.LENGTH_SHORT).show();
            /*((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction()
            .replace(R.id.nav_host_fragment,new HomeFragment()).commit();*/
            mListener.getSelectedImage(imageList.get(selectedPhoto));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentGalleryMain)
        {
            mListener = (FragmentGalleryMain) context;
        }
        else
        {
            throw new RuntimeException(context.toString()+" must implement Fragment Gallery Main");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface FragmentGalleryMain
    {
        void getSelectedImage(String imageUri);
    }
}