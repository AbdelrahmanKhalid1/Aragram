package com.example.aragram.ui.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aragram.R;
import com.example.aragram.model.User;
import com.example.aragram.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Edit_Profile extends AppCompatActivity {

    TextView changeProfile;
    EditText changeUserName;
    EditText changePassword;
    EditText changeWebsite;
    EditText changeBio;
    ImageView closeProfile;
    ImageView saveProfile;
    EditProfileViewModel editProfileViewModel;
    ProgressBar progressBar;
    ImageView profilePicture;
    private Uri mIUri;
    ContentResolver cR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initViews();
        editProfileViewModel= new ViewModelProvider(this).get(EditProfileViewModel.class);
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Edit_Profile.this, "hi "+changeWebsite.getText().toString(), Toast.LENGTH_SHORT).show();
                User user=new User(changeUserName.getText().toString(),changePassword.getText().toString()
                ,"","",changeBio.getText().toString(),changeWebsite.getText().toString());


                editProfileViewModel.makeChange(user);
                progressBar.setVisibility(View.VISIBLE);
            }

        });
        editProfileViewModel.getChangeStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Edit_Profile.this, "Edit Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        editProfileViewModel.getPhotoStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Edit_Profile.this, "Profile Picture Change Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViews() {
        changeProfile=findViewById(R.id.textView6);
        changeUserName=findViewById(R.id.changed_username);
        changePassword=findViewById(R.id.changed_password);
        changeBio=findViewById(R.id.bio_changed);
        changeWebsite=findViewById(R.id.website_changed);
        closeProfile=findViewById(R.id.close_profile);
        saveProfile=findViewById(R.id.change_profile);
        progressBar=findViewById(R.id.edit_progress_bar);
        profilePicture=findViewById(R.id.profile_picture_edit);
        cR=getContentResolver();
        changeUserName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1&&data!=null&&data.getData()!=null) {
                mIUri=data.getData();
               // InputStream inputStream = getContentResolver().openInputStream(data.getData());
               // Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
              //  profilePicture.setImageBitmap(bitmap);
            Picasso.with(this).load(mIUri).into(profilePicture);
            String s= getFileExtension(mIUri);
            editProfileViewModel.uploadFile(s,mIUri);
            progressBar.setVisibility(View.VISIBLE);




        }

    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void changeProfilePhoto(View view) {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Pick an Image"),1);


    }
}