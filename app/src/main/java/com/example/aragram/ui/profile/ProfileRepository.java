package com.example.aragram.ui.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.aragram.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ProfileRepository {
    MutableLiveData<User> userMutableLiveData=new MutableLiveData<>();
    private DocumentReference documentReference;
    Context context;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    User user;

    public ProfileRepository(Context context) {
        this.context = context;
        db= FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        documentReference=db.collection("profiles").document(firebaseUser.getDisplayName());
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getInstance().getReference().child("imagesPP/"+mAuth.getCurrentUser().getDisplayName());
    }

    public void getProfileData()
    {
        Log.d("page2", "getProfileData: "+"profiles/"+firebaseUser.getDisplayName());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    user= task.getResult().toObject(User.class);
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            user.setUserProfilePicture(uri.toString());
                            userMutableLiveData.setValue(user);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            userMutableLiveData.setValue(user);
                        }
                    });


                }
            }
        });


    }

}
