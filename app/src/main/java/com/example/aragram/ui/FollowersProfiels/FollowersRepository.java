package com.example.aragram.ui.FollowersProfiels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.aragram.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FollowersRepository {
    MutableLiveData<List<User>> followersList =new MutableLiveData<>();
    MutableLiveData<List<User>> followingList=new MutableLiveData<>();

    public MutableLiveData<List<User>> getFollowingList() {
        return followingList;
    }

    List<User> test=new ArrayList<>();
    FirebaseFirestore db;

    public FollowersRepository() {
        db=FirebaseFirestore.getInstance();

    }

    public MutableLiveData<List<User>> getFollowersList() {
        return followersList;
    }

    public  void getFollowersProfiles(String username)
    {
        db.collection("profiles").document(username)
                .collection("followerPersons").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    //List<String> followersNames=new ArrayList<>();
                    QuerySnapshot querySnapshot =task.getResult();
                    List<DocumentSnapshot> snapshotList=querySnapshot.getDocuments();
                    for(int i=0;i<snapshotList.size();i++)
                    {

                        String s=(snapshotList.get(i).getString("name"));
                        db.collection("profiles").document(s)
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    DocumentSnapshot documentSnapshot=task.getResult();
                                    if(documentSnapshot.exists())
                                    {
                                        Log.d("memo", "onComplete: hel");
                                        test.add(documentSnapshot.toObject(User.class));
                                        followersList.setValue(test);
                                    }

                                }
                            }
                        });

                    }


                }
            }
        });

    }
    public void getFollowingProfiles(String username)
    {
        db.collection("profiles").document(username)
                .collection("followingPerson").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    //List<String> followersNames=new ArrayList<>();
                    QuerySnapshot querySnapshot =task.getResult();
                    List<DocumentSnapshot> snapshotList=querySnapshot.getDocuments();
                    for(int i=0;i<snapshotList.size();i++)
                    {

                        String s=(snapshotList.get(i).getString("name"));
                        db.collection("profiles").document(s)
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    DocumentSnapshot documentSnapshot=task.getResult();
                                    if(documentSnapshot.exists())
                                    {
                                        Log.d("memo", "onComplete: hel");
                                        test.add(documentSnapshot.toObject(User.class));
                                        followingList.setValue(test);
                                    }

                                }
                            }
                        });

                    }


                }
            }
        });


    }
}
