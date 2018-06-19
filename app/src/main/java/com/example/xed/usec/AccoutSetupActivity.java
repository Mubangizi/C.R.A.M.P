package com.example.xed.usec;

import android.Manifest;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccoutSetupActivity extends AppCompatActivity {
    /*

    private Toolbar setupToolbar;
    private CircleImageView msetupImage;
    private Uri setupimageUrl = null;
    private final int GALLERY_REQUEST =1;
    private Button msetupbtn;
    private EditText msetupuserName;
    private ProgressBar msetupprogress;

    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mdatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accout_setup);

        msetupImage = (CircleImageView) findViewById(R.id.setupcircleimage);
        msetupbtn =(Button) findViewById(R.id.save_settings_btn);
        msetupuserName = (EditText) findViewById(R.id.username_settingname);
        msetupprogress = findViewById(R.id.setup_progressBar);


        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        setupToolbar = findViewById(R.id.setuptoolbar);
        setSupportActionBar(setupToolbar);
        getSupportActionBar().setTitle("Account Setup");


        msetupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                    if(ContextCompat.checkSelfPermission(AccoutSetupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(AccoutSetupActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(AccoutSetupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }else {
                       imagepicker();
                    }

                }else {
                    imagepicker();
                }


            }
        });


        msetupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username_val = msetupuserName.getText().toString();
                msetupprogress.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(username_val) && setupimageUrl != null){

                    final String user_id =FirebaseAuth.getInstance().getUid();

                    final StorageReference imagepath =storageReference.child("Profile_Images").child(user_id+".jpg");

                    imagepath.putFile(setupimageUrl).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if(task.isSuccessful()){

                                Task<Uri> downloadurl = imagepath.getDownloadUrl();

                                DatabaseReference userref = mdatabase.push();
                                userref.child("username").setValue(username_val);
                                userref.child("profileimages").setValue(downloadurl.toString());


                                Toast.makeText(AccoutSetupActivity.this, "Settings Updated", Toast.LENGTH_LONG).show();
                                Intent homepageIntent = new Intent(AccoutSetupActivity.this, MainActivity.class);
                                startActivity(homepageIntent);
                                finish();

                            } else {

                                String error = task.getException().getMessage();
                                Toast.makeText(AccoutSetupActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                                msetupprogress.setVisibility(View.INVISIBLE);
                            }

                        }
                    });

                } else {
                    Toast.makeText(AccoutSetupActivity.this, "Missing fields: " , Toast.LENGTH_LONG).show();
                    msetupprogress.setVisibility(View.INVISIBLE);
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK) {

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

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

    public  void imagepicker(){

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);

    }
    */
}
