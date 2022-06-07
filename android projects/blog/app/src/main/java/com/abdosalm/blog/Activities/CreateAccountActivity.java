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

import com.abdosalm.blog.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private Button createAccountButton;
    private ImageButton profilePic;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference mFirebaseStorage;
    private Uri resultUri = null;
    private ProgressDialog mProgressDialog;
    private static final int GALLERY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("MUser");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance().getReference().child("MBlog_Profile_Pics");

        mProgressDialog = new ProgressDialog(this);

        firstName = findViewById(R.id.firstNameAct);
        lastName = findViewById(R.id.lastNameAct);
        email = findViewById(R.id.emailAct);
        password = findViewById(R.id.passwordAct);
        createAccountButton = findViewById(R.id.createAccountAct);
        profilePic = findViewById(R.id.profilePick);

        profilePic.setOnClickListener(v->{
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_CODE);
        });

        createAccountButton.setOnClickListener(v->{
            createNewAccount();
        });
    }

    private void createNewAccount() {
        String name = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String em = email.getText().toString().trim();
        String pwd = password.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lName) && !TextUtils.isEmpty(em) && !TextUtils.isEmpty(pwd)){
            mProgressDialog.setMessage("Creating Account");
            mProgressDialog.show();

            mAuth.createUserWithEmailAndPassword(em,pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    if (authResult != null){
                        StorageReference imagePath = mFirebaseStorage.child("MBlog_Profile_Pics").child(resultUri.getLastPathSegment());
                        imagePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                String userId = mAuth.getCurrentUser().getUid();
                                DatabaseReference currentUserDb = mDatabaseReference.child(userId);
                                currentUserDb.child("firstname").setValue(name);
                                currentUserDb.child("lastname").setValue(lName);
                                currentUserDb.child("image").setValue(resultUri.toString());

                                mProgressDialog.dismiss();
                                //send users to part list
                                Intent intent = new Intent(CreateAccountActivity.this,PostListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK){
            Uri mImageUri = data.getData();
            CropImage.activity(mImageUri).setAspectRatio(1,1).setGuidelines(CropImageView.Guidelines.ON).start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                resultUri = result.getUri();
                profilePic.setImageURI(resultUri);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
    }
}