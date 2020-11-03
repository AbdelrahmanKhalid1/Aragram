package com.example.aragram.ui.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginRepository {
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    Context context;
    MutableLiveData<Boolean> flag=new MutableLiveData<>();
    MutableLiveData<Boolean> checkRegister= new MutableLiveData<>();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    Map<String,Object> anagramProfile= new HashMap<>();

    public MutableLiveData<Boolean> getFlag() {
        return flag;
    }

    public LoginRepository(Context context) {
        this.context = context;

    }

    public MutableLiveData<Boolean> getCheckRegister() {
        return checkRegister;
    }

    public  void login(String username, String password)
    {


        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            flag.setValue(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                            flag.setValue(false);
                        }

                        // ...
                    }
                });


    }

    public void register(final User user)
    {

        Log.d("hegazy", "register: ");
        mAuth.createUserWithEmailAndPassword(user.getUsername()+"@gmail.com", user.getPassword())
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
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
                            anagramProfile.put("age",user.getAge());
                            anagramProfile.put("website",user.getWebsite());
                            db.collection("profiles").document(user.getUsername())
                                    .set(anagramProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    checkRegister.setValue(true);
                                    Toast.makeText(context, "Register Success.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser firebaseUser=mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates= new UserProfileChangeRequest.Builder()
                                            .setDisplayName(user.getUsername()).build();
                                    firebaseUser.updateProfile(profileUpdates);


                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            checkRegister.setValue(true);
                                            Toast.makeText(context, "Register profile failure.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });


                        }

                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("saasa", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Register failed.",
                                    Toast.LENGTH_SHORT).show();
                            checkRegister.setValue(false);
                        }

                        // ...
                    }
                });






    }


}
