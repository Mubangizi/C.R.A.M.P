package com.example.xed.usec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {


    private ImageButton mimageBUtton;
    private EditText mtitleEditText;
    private EditText mdescEditText;
    private Button msubmitButton;
    private Uri mimageUri=null;
    private ProgressDialog mprogress;

    private DatabaseReference mdatabase;   //root directory
    private StorageReference mstorage;
    private FirebaseAuth firebaseAuth;
    private String userid;



    static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mimageBUtton = (ImageButton) findViewById(R.id.imageSelect);
        mtitleEditText = (EditText) findViewById(R.id.titleSelect);
        mdescEditText = (EditText) findViewById(R.id.descSelect);
        msubmitButton = (Button) findViewById(R.id.submitBtn);
        mprogress =new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        mstorage = FirebaseStorage.getInstance().getReference();    //this gets the root storage on our database on the server
        mdatabase = FirebaseDatabase.getInstance().getReference("Posts");


        mimageBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(PostActivity.this);
            }
        });


        msubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }

        });

    }

    //method for posting
    private void startPosting() {
        mprogress.setMessage("Posting");
        mprogress.show();
        final String title_val = mtitleEditText.getText().toString().trim();
        final String desc_val = mdescEditText.getText().toString().trim();
        final String entry_id = String.valueOf(System.currentTimeMillis());
        final String user_id =String.valueOf(userid);
        final Long timestamp = System.currentTimeMillis();

        //checking if title, desc and image are not empty
        if(!TextUtils.isEmpty(title_val ) && !TextUtils.isEmpty(desc_val) && mimageUri != null){

            //path where posts are to be stored on the server
            final StorageReference filePath = mstorage.child("post_images").child(UUID.randomUUID().toString());

            //uploading files
           filePath.putFile(mimageUri)
               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                       taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                           @Override
                           public void onComplete(@NonNull Task<Uri> task) {
                               String downloaduri = String.valueOf(task.getResult());

                               Post post = new Post(entry_id,title_val,desc_val,downloaduri,user_id,timestamp);
                               mdatabase.child(post.getEntry_id()).setValue(post);

                               mprogress.dismiss();

                               startActivity(new Intent(PostActivity.this, MainActivity.class));
                               toastMessage("Posted");

                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {

                           }
                       });
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       mprogress.dismiss();
                       toastMessage("Failed to post");
                   }
               })
               .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                       double progress =(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                       mprogress.setMessage("Posting "+(int)progress+"%");
                   }
               });
        }else {
            Toast.makeText(PostActivity.this, "Missing fields: ", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mimageUri = result.getUri();
                mimageBUtton.setImageURI(mimageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}

