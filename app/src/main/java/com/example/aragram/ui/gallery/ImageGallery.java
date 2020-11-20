package com.example.aragram.ui.gallery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class ImageGallery {

    private static final String TAG = "ImageGallery";
    //TODO do it in background thread
    public static ArrayList<String> listOfImage(Context context) {
        ArrayList<String> imageList = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String path;
        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String orderBy = MediaStore.Video.Media.DATE_TAKEN;

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                path = cursor.getString(0);
                Log.d(TAG, "listOfImage: " + path);
                imageList.add(path);
            }
            cursor.close();
        }
        return imageList;
    }
}
