package com.example.aragram.ui.profile;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.aragram.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditProfileRepository {
    MutableLiveData<Boolean> changeStatus=new MutableLiveData<>();
    MutableLiveData<Boolean> uploadPhotoStatus=new MutableLiveData<>();
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public MutableLiveData<Boolean> getUploadPhotoStatus() {
        return uploadPhotoStatus;
    }

    public EditProfileRepository() {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        db= FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    public MutableLiveData<Boolean> getChangeStatus() {
        return changeStatus;
    }
    public void makeChange(final User user)
    {
        final String oldname=firebaseUser.getDisplayName();
        firebaseUser.updateEmail(user.getUsername()+"@gmail.com").addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        firebaseUser.updatePassword(user.getPassword())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("lol", "onSuccess: 70%"+Thread.currentThread().getName());
                                        Map<String,Object> profile=new HashMap<>();
                                        profile.put("bio",user.getBio());
                                        profile.put("website",user.getWebsite());
                                        profile.put("password",user.getPassword());
                                        profile.put("username",user.getUsername());

                                        db.collection("profiles").document(oldname)
                                                .update(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                changeStatus.setValue(true);
                                                Log.d("lol", "onSuccess: done ");
                                            }
                                        });
                                    }
                                });

                    }
                }
        );



    }

    public void uploadFile(String s,Uri uri)
    {

        final String randomKey= UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("imagesPP/"+FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        uploadPhotoStatus.setValue(true);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }

}
