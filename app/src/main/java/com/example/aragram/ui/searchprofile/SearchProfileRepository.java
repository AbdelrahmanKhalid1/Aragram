package com.example.aragram.ui.searchprofile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.aragram.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProfileRepository {
    MutableLiveData<Boolean> mIsUpdating=new MutableLiveData<>();
    MutableLiveData<Boolean> mIsFollowed=new MutableLiveData<>();
    MutableLiveData<Boolean> mIsUnFollowed=new MutableLiveData<>();

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    public MutableLiveData<Boolean> getmIsFollowed() {
        return mIsFollowed;
    }

    public MutableLiveData<Boolean> getmIsUnFollowed() {
        return mIsUnFollowed;
    }

    public SearchProfileRepository() {
        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

    }
    public void unFollowPerson(final String username)
    {
        db.collection("profiles").document(mAuth.getCurrentUser().getDisplayName())
                .collection("followingPerson").document(username).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                db.collection("profiles").document(username).collection("followerPersons")
                        .document(mAuth.getCurrentUser().getDisplayName()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.collection("profiles").document(mAuth.getCurrentUser().getDisplayName())
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot documentSnapshot=task.getResult();
                                int numOfFollowing=documentSnapshot.getLong("following").intValue()-1;
                                Map<String,Object> map=new HashMap<>();
                                map.put("following",numOfFollowing);
                                db.collection("profiles").document(mAuth.getCurrentUser().getDisplayName())
                                        .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        db.collection("profiles").document(username)
                                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                DocumentSnapshot documentSnapshot1=task.getResult();
                                                int numOfFollower=documentSnapshot1.getLong("followers").intValue()-1;
                                                Map<String,Object> map1=new HashMap<>();
                                                map1.put("followers",numOfFollower);
                                                db.collection("profiles").document(username)
                                                        .update(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mIsUnFollowed.setValue(true);
                                                    }
                                                });


                                            }
                                        });
                                    }
                                });

                            }
                        });

                    }
                });

            }
        });
    }
    public void checkPerson(final User user)
    {

        db.collection("profiles").document(user.getUsername())
                .collection("followerPersons").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {

                    QuerySnapshot querySnapshot=task.getResult();
                    List<DocumentSnapshot> allDocuments= querySnapshot.getDocuments();
                    for(int i=0;i<allDocuments.size();i++)
                    {

                        if(allDocuments.get(i).getString("name").equals(mAuth.getCurrentUser().getDisplayName()))
                        {
                            Log.d("as", "checkPerson: hi"+allDocuments.get(i).getString("name"));
                            mIsFollowed.setValue(true);
                        }
                    }
                }

            }
        });


    }

    public void followPerson(final String name)
    {
        Map<String,Object> person=new HashMap<>();
        person.put("name",name);
        db.collection("profiles").document(mAuth.getCurrentUser().getDisplayName()).collection("followingPerson")
                .document(name).set(person).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Log.d("7amada", "onComplete: "+name);
                    Map<String,Object> person1=new HashMap<>();
                    person1.put("name",mAuth.getCurrentUser().getDisplayName());
                    db.collection("profiles").document(name).collection("followerPersons")
                            .document(mAuth.getCurrentUser().getDisplayName()).set(person1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                   final Map<String,Object> m1=new HashMap<>();

                                    db.collection("profiles").document(mAuth.getCurrentUser().getDisplayName())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            DocumentSnapshot documentSnapshot= task.getResult();
                                            int numOfFollowing = documentSnapshot.getLong("following").intValue();
                                            m1.put("following",numOfFollowing+1);
                                            db.collection("profiles").document(mAuth.getCurrentUser().getDisplayName())
                                                    .update(m1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    db.collection("profiles").document(name).get()
                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                    DocumentSnapshot documentSnapshot= task.getResult();
                                                                    int numOfFollower = documentSnapshot.getLong("followers").intValue();
                                                                    final Map<String,Object> m2=new HashMap<>();
                                                                    m2.put("followers",numOfFollower+1);
                                                                    db.collection("profiles").document(name).update(m2)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    mIsUpdating.setValue(true);
                                                                                }
                                                                            });

                                                                }
                                                            });

                                                }
                                            });
                                        }
                                    });


                                }
                        }
                    });

                }

            }
        });

    }

    public MutableLiveData<Boolean> getmIsUpdating() {
        return mIsUpdating;
    }
}
