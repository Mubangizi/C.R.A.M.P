package com.example.xed.usec;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountSetupActivity extends AppCompatActivity {

    private Toolbar setupToolbar;
    private CircleImageView msetupImage;
    private Uri setupimageUrl = null;
    private Button msetupbtn;
    private EditText msetupuserName;
    private ProgressBar msetupprogress;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mdatabase;
    private  String user_id;
    private String username_val;
    private String TAG = "AccountSetupActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

        msetupImage = (CircleImageView) findViewById(R.id.setupcircleimage);
        msetupbtn = (Button) findViewById(R.id.save_settings_btn);
        msetupuserName = (EditText) findViewById(R.id.username_settingname);
        msetupprogress = findViewById(R.id.setup_progressBar);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        user_id = firebaseAuth.getCurrentUser().getUid();

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        setupToolbar = findViewById(R.id.setuptoolbar);
        setSupportActionBar(setupToolbar);
        getSupportActionBar().setTitle("Account Setup");

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String userval = String.valueOf(dataSnapshot.child(user_id).child("username").getValue());
                String imageval = String.valueOf(dataSnapshot.child(user_id).child("profileImage").getValue());

                msetupuserName.setText(userval);
                Glide.with(getApplicationContext()).load(imageval).into(msetupImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        msetupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(AccountSetupActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(AccountSetupActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(AccountSetupActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {
                        imagepicker();
                    }
                } else {
                    imagepicker();
                }
            }
        });


        msetupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msetupprogress.setVisibility(View.VISIBLE);

                username_val = msetupuserName.getText().toString().trim();

                Log.e(TAG,"Save button: Username: "+username_val+" Image uri: "+setupimageUrl.toString());

                if (!TextUtils.isEmpty(username_val) && setupimageUrl != null) {

                    user_id = FirebaseAuth.getInstance().getUid();
                    final StorageReference imagepath = storageReference.child("Profile_Images").child(user_id+ ".jpg");

                    imagepath.putFile(setupimageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri downloadurl = task.getResult();
                                    String profileimage = String.valueOf(downloadurl);

                                    Post post = new Post(username_val,profileimage);
                                    mdatabase.child(user_id).setValue(post);

                                    Toast.makeText(AccountSetupActivity.this, "Settings Updated", Toast.LENGTH_LONG).show();
                                    Intent homepageIntent = new Intent(AccountSetupActivity.this, MainActivity.class);
                                    startActivity(homepageIntent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String error = taskSnapshot.getTask().getException().getMessage();
                                    Toast.makeText(AccountSetupActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                                    msetupprogress.setVisibility(View.INVISIBLE);
                                }
                            });

                        }
                    });
                } else {
                    Toast.makeText(AccountSetupActivity.this, "Missing fields: ", Toast.LENGTH_LONG).show();
                    msetupprogress.setVisibility(View.INVISIBLE);
                }
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                setupimageUrl = result.getUri();
                msetupImage.setImageURI(setupimageUrl);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

            }
        }
    }

    public void imagepicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(AccountSetupActivity.this);
    }

}

