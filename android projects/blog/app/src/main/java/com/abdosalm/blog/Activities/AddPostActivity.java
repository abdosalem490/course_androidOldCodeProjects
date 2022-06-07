package com.abdosalm.blog.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.abdosalm.blog.Model.Blog;
import com.abdosalm.blog.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {
    private ImageButton mPostImage;
    private EditText mPostTitle;
    private EditText mPostDesc;
    private Button mSubmitButton;

    private DatabaseReference mPostDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private StorageReference mStorage;

    private ProgressDialog mProgress;

    private static final int GALLERY_CODE = 1;
    private Uri mImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mPostDatabase = FirebaseDatabase.getInstance().getReference().child("MBlog");
        mStorage = FirebaseStorage.getInstance().getReference();

        mPostImage = findViewById(R.id.imageButton);
        mPostTitle = findViewById(R.id.postTitleEt);
        mPostDesc = findViewById(R.id.postDescriptionEt);
        mSubmitButton = findViewById(R.id.submitPostButton);
        mProgress = new ProgressDialog(this);

        mPostImage.setOnClickListener(v->{
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_CODE);
        });
        mSubmitButton.setOnClickListener(v->{
            // Posting to our database
            startPosting();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK){
            mImageUri = data.getData();
            mPostImage.setImageURI(mImageUri);
        }
    }

    private void startPosting() {
        mProgress.setMessage("Posting to blog...");
        mProgress.show();

        String titleVal = mPostTitle.getText().toString().trim();
        String descVal = mPostDesc.getText().toString().trim();
        if (!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal) && mImageUri != null){
            // start the uploading
            StorageReference filepath = mStorage.child("MBlog_images").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();

                    DatabaseReference newPost = mPostDatabase.push();
                    Map<String,String> dataToSave = new HashMap<>();
                    dataToSave.put("title",titleVal);
                    dataToSave.put("desc",descVal);
                    dataToSave.put("image",downloadUrl.toString());
                    dataToSave.put("timestamp", String.valueOf(System.currentTimeMillis()));
                    dataToSave.put("userid",mUser.getUid());
                    newPost.setValue(dataToSave);
                    mProgress.dismiss();
                    startActivity(new Intent(AddPostActivity.this,PostListActivity.class));
                    finish();
                }
            });
        }
    }
}