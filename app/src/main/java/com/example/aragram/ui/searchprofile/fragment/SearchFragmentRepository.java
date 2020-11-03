package com.example.aragram.ui.searchprofile.fragment;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchFragmentRepository {
    FirebaseFirestore db;

    MutableLiveData<List<User>> usersResult=new MutableLiveData<>();
    MutableLiveData<List<User>> filterdUsers=new MutableLiveData<>();
    List<User> list=new ArrayList<>();
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public SearchFragmentRepository() {
        db=FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();


    }

    public MutableLiveData<List<User>> getUsersResult() {
        return usersResult;

    }

    public MutableLiveData<List<User>> getFilterdUsers() {
        return filterdUsers;
    }

    public void getProfiles()
    {
        db.collection("profiles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {

                    for(QueryDocumentSnapshot document :task.getResult())
                    {
                        final User u= document.toObject(User.class);
                        if(u.getUsername().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()))
                        {
                            continue;
                        }
                       /* try {
                            //storageReference.child(u.getUsername()).;
                            storageReference = storage.getInstance().getReference().child("imagesPP/"+u.getUsername());
                            Log.d("hax", "onComplete: "+u.getUsername());
                            final File localFile= File.createTempFile(u.getUsername(),"jpg");
                            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                        u.setProfilePicture(bitmap);
                                    list.add(u);
                                    usersResult.setValue(list);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    list.add(u);
                                    usersResult.setValue(list);
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        */
                        storageReference = storage.getInstance().getReference().child("imagesPP/"+u.getUsername());
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                u.setUserProfilePicture(uri.toString());
                                list.add(u);
                                usersResult.setValue(list);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                list.add(u);
                                usersResult.setValue(list);

                            }
                        });





                    }


                }
            }
        });

    }
    public void getFilterProfiles(String s)
    {
        List<User> filter=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getUsername().contains(s))
            {
               filter.add(list.get(i));
            }

        }
        filterdUsers.setValue(filter);

    }
}
