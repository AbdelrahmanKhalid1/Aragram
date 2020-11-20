package com.example.aragram.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import com.example.aragram.ui.MainActivity;
import com.example.aragram.model.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//TODO make Class SingleTone
//TODO Remove Async Task
public class PostRepository {

    public static PostRepository instance;
    private DatabaseReference databaseRef;
    private StorageReference storageRef;
    private MutableLiveData<ArrayList<Post>> allPosts;
    private Application mApplication;
    private ArrayList<Post> posts;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionPost = firestore.collection("Posts");

    public PostRepository(Application application) {
        this.mApplication = application;
    }

    public void insetNewPost(Post post) {
        collectionPost.document(MainActivity.loginUser.getUsername()).
                collection("post").add(post);
    }

    public void updatePostData(Post post) {
        DocumentReference docRef = collectionPost.document(MainActivity.loginUser.getUsername())
                .collection("post").document(post.getDocumentId());
        Map<String, Object> newValue = new HashMap<>();
        newValue.put("commentList", post.getCommentList());
        newValue.put("description", post.getDescription());
        newValue.put("like", post.isLike());
        newValue.put("likeList", post.getLikeList());
        newValue.put("postImage", post.getPostImage());
        newValue.put("save", post.isSave());
        newValue.put("time", post.getTime());
        newValue.put("userProfile", post.getUserProfile());

        docRef.set(newValue, SetOptions.merge());
    }


    public MutableLiveData<ArrayList<Post>> getAllData() {
        posts = new ArrayList<>();
        allPosts = new MutableLiveData<>();
        new AyncTask().execute();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                allPosts.setValue(posts);
                //return allPosts;
            }
        }, 2000);
        return allPosts;
    }

    public class AyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            firestore.collection("Posts").document(MainActivity.loginUser.getUsername())
                    .collection("post").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot item : documentSnapshotList) {
                                Post postItem = item.toObject(Post.class);
                                postItem.setDocumentId(item.getId());
                                posts.add(postItem);
                            }
                        }
                    });
            return null;
        }
    }
}
