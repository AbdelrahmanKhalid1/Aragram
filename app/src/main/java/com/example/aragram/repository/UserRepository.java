package com.example.aragram.repository;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aragram.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class UserRepository {
    int index = 0;
    public static UserRepository userRepository = null;
    FirebaseFirestore db;
    MutableLiveData<List<User>> followersList;
    MutableLiveData<List<User>> followingList;
    List<User> test = new ArrayList<>();
    FirebaseAuth mAuth;
    MutableLiveData<Boolean> flag;
    MutableLiveData<Boolean> checkRegister;
    Map<String, Object> anagramProfile;
    MutableLiveData<Boolean> changeStatus;
    MutableLiveData<Boolean> uploadPhotoStatus;
    FirebaseUser firebaseUser;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DocumentReference documentReference;
    MutableLiveData<User> userMutableLiveData;
    MutableLiveData<List<User>> usersResult;
    MutableLiveData<List<User>> filterdUsers;
    User user;
    List<User> list;
    MutableLiveData<Boolean> mIsUpdating;
    MutableLiveData<Boolean> mIsFollowed;
    MutableLiveData<Boolean> mIsUnFollowed;


    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public UserRepository() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        followersList = new MutableLiveData<>();
        followingList = new MutableLiveData<>();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        flag = new MutableLiveData<>();
        checkRegister = new MutableLiveData<>();
        anagramProfile = new HashMap<>();
        changeStatus = new MutableLiveData<>();
        uploadPhotoStatus = new MutableLiveData<>();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        userMutableLiveData = new MutableLiveData<>();
        usersResult = new MutableLiveData<>();
        filterdUsers = new MutableLiveData<>();
        list = new ArrayList<>();
        mIsUpdating = new MutableLiveData<>();
        mIsFollowed = new MutableLiveData<>();
        mIsUnFollowed = new MutableLiveData<>();
    }

    public LiveData<Boolean> getFlag() {
        return flag;
    }

    public LiveData<Boolean> getCheckRegister() {
        return checkRegister;
    }

    public LiveData<List<User>> getFollowingList() {
        return followingList;
    }

    public LiveData<List<User>> getFollowersList() {
        return followersList;
    }

    public LiveData<Boolean> getUploadPhotoStatus() {
        return uploadPhotoStatus;
    }

    public LiveData<Boolean> getChangeStatus() {
        return changeStatus;
    }

    public LiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public LiveData<List<User>> getUsersResult() {
        return usersResult;
    }

    public LiveData<Boolean> getmIsFollowed() {
        return mIsFollowed;
    }

    public LiveData<Boolean> getmIsUnFollowed() {
        return mIsUnFollowed;
    }

    public LiveData<Boolean> getmIsUpdating() {
        return mIsUpdating;
    }

    public LiveData<List<User>> getFilterdUsers() {
        return filterdUsers;
    }

    public void getFollowersProfiles(String username) {
        test.clear();
        db.collection("profiles").document(username)
                .collection("followerPersons").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //List<String> followersNames=new ArrayList<>();
                    QuerySnapshot querySnapshot = task.getResult();
                    List<DocumentSnapshot> snapshotList = querySnapshot.getDocuments();
                    for (int i = 0; i < snapshotList.size(); i++) {

                        String s = (snapshotList.get(i).getString("name"));
                        db.collection("profiles").document(s)
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if (documentSnapshot.exists()) {
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

    public void getFollowingProfiles(String username) {
        test.clear();
        db.collection("profiles").document(username)
                .collection("followingPerson").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //List<String> followersNames=new ArrayList<>();
                    QuerySnapshot querySnapshot = task.getResult();
                    List<DocumentSnapshot> snapshotList = querySnapshot.getDocuments();
                    for (int i = 0; i < snapshotList.size(); i++) {

                        String s = (snapshotList.get(i).getString("name"));
                        db.collection("profiles").document(s)
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if (documentSnapshot.exists()) {
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

    public void login(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            flag.setValue(true);
                        } else {

                            flag.setValue(false);
                        }


                    }
                });


    }

    public void register(final User user) {

        Log.d("hegazy", "register: ");
        mAuth.createUserWithEmailAndPassword(user.getUsername() + "@gmail.com", user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("sasa", "createUserWithEmail:success");
                            anagramProfile.put("username", user.getUsername());
                            anagramProfile.put("password", user.getPassword());
                            anagramProfile.put("gender", user.getGender());
                            anagramProfile.put("following", user.getFollowing());
                            anagramProfile.put("followers", user.getFollowers());
                            anagramProfile.put("bio", user.getBio());
                            anagramProfile.put("posts", user.getPosts());
                            anagramProfile.put("age", user.getAge());
                            anagramProfile.put("website", user.getWebsite());
                            db.collection("profiles").document(user.getUsername().toLowerCase() + "@gmail.com")
                                    .set(anagramProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    checkRegister.setValue(true);
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(user.getUsername()).build();
                                    firebaseUser.updateProfile(profileUpdates);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    checkRegister.setValue(true);
                                }
                            });
                        } else {
                            checkRegister.setValue(false);
                        }
                    }
                });
    }

    public void makeChange(final User user) {
        final String oldname = firebaseUser.getDisplayName();
        firebaseUser.updateEmail(user.getUsername() + "@gmail.com").addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        firebaseUser.updatePassword(user.getPassword())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("lol", "onSuccess: 70%" + Thread.currentThread().getName());
                                        Map<String, Object> profile = new HashMap<>();
                                        profile.put("bio", user.getBio());
                                        profile.put("website", user.getWebsite());
                                        profile.put("password", user.getPassword());
                                        profile.put("username", user.getUsername());

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

    public void uploadFile(String s, final Uri uri) {

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("imagesPP/" + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

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

    public void getProfileData() {
        String userEmail = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
        Log.d("TAG", "getProfileData: " + userEmail);
        documentReference = db.collection("profiles").document(userEmail);
        storageReference = storage.getInstance().getReference().child("imagesPP/" + mAuth.getCurrentUser().getDisplayName());
        Log.d("page2", "getProfileData: " + "profiles/" + firebaseUser.getDisplayName());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    user = task.getResult().toObject(User.class);
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

    public void getProfiles() {

        list.clear();

        db.collection("profiles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        final User u = document.toObject(User.class);
                        if (u.getUsername().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
                            continue;
                        }

                        storageReference = storage.getInstance().getReference().child("imagesPP/" + u.getUsername());
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

    public void getFilterProfiles(String s) {
        List<User> filter = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().contains(s)) {
                filter.add(list.get(i));
            }

        }
        filterdUsers.setValue(filter);

    }

    public void followPerson(final String name) {
        Map<String, Object> person = new HashMap<>();
        person.put("name", name);
        db.collection("profiles").document(mAuth.getCurrentUser().getDisplayName()).collection("followingPerson")
                .document(name).set(person).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("7amada", "onComplete: " + name);
                    Map<String, Object> person1 = new HashMap<>();
                    person1.put("name", mAuth.getCurrentUser().getDisplayName());
                    db.collection("profiles").document(name).collection("followerPersons")
                            .document(mAuth.getCurrentUser().getDisplayName()).set(person1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        final Map<String, Object> m1 = new HashMap<>();

                                        db.collection("profiles").document(mAuth.getCurrentUser().getDisplayName())
                                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                int numOfFollowing = documentSnapshot.getLong("following").intValue();
                                                m1.put("following", numOfFollowing + 1);
                                                db.collection("profiles").document(mAuth.getCurrentUser().getDisplayName())
                                                        .update(m1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        db.collection("profiles").document(name).get()
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        DocumentSnapshot documentSnapshot = task.getResult();
                                                                        int numOfFollower = documentSnapshot.getLong("followers").intValue();
                                                                        final Map<String, Object> m2 = new HashMap<>();
                                                                        m2.put("followers", numOfFollower + 1);
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

    public void unFollowPerson(final String username) {
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
                                DocumentSnapshot documentSnapshot = task.getResult();
                                int numOfFollowing = documentSnapshot.getLong("following").intValue() - 1;
                                Map<String, Object> map = new HashMap<>();
                                map.put("following", numOfFollowing);
                                db.collection("profiles").document(mAuth.getCurrentUser().getDisplayName())
                                        .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        db.collection("profiles").document(username)
                                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                DocumentSnapshot documentSnapshot1 = task.getResult();
                                                int numOfFollower = documentSnapshot1.getLong("followers").intValue() - 1;
                                                Map<String, Object> map1 = new HashMap<>();
                                                map1.put("followers", numOfFollower);
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

    public void checkPerson(final User user) {

        db.collection("profiles").document(user.getUsername())
                .collection("followerPersons").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    List<DocumentSnapshot> allDocuments = querySnapshot.getDocuments();
                    for (int i = 0; i < allDocuments.size(); i++) {
                        if (allDocuments.get(i).getString("name").equals(mAuth.getCurrentUser().getDisplayName())) {
                            Log.d("as", "checkPerson: hi" + allDocuments.get(i).getString("name"));
                            mIsFollowed.setValue(true);
                        }
                    }
                }
            }
        });
    }
}
