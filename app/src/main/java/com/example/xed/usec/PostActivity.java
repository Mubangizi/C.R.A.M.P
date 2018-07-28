package com.example.xed.usec;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
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
    private Uri mimageUri = null;
    private ProgressDialog mprogress;

    private DatabaseReference mdatabase;   //root directory
    private StorageReference mstorage;
    private FirebaseAuth firebaseAuth;
    private String userid;
    private Double latitude_val;
    private Double longitude_val;
    private int REQUEST_CODE=10;

    //location
    private LocationManager locationManager;
    private LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mimageBUtton = (ImageButton) findViewById(R.id.imageSelect);
        mtitleEditText = (EditText) findViewById(R.id.titleSelect);
        mdescEditText = (EditText) findViewById(R.id.descSelect);
        msubmitButton = (Button) findViewById(R.id.submitBtn);
        mprogress = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        mstorage = FirebaseStorage.getInstance().getReference();    //this gets the root storage on our database on the server
        mdatabase = FirebaseDatabase.getInstance().getReference("Posts");

        //getting location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);



        /*
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude_val = location.getLatitude();
                longitude_val = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //SETTINGS INTENT
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        */

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.INTERNET}
                        , 10);
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            } else {
                submitPostButton();
            }
        }*/
        submitPostButton();
        //locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);


        mimageBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(PostActivity.this);
            }
        });
    }


    private void submitPostButton() {
        msubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(getApplicationContext(), "Please enable location services", Toast.LENGTH_SHORT).show();
                }

                int permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    LocationRequest locationRequest= new LocationRequest();

                    //Specify how app should request the device’s location//
                    locationRequest.setInterval(10000);
                    locationRequest.setFastestInterval(5000);

                    //Get the most accurate location data available//
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getApplicationContext());

                    //...then request location updates//
                    client.requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {

                            //Get a reference to the database, to perform read and write operations//
                            Location location = locationResult.getLastLocation();
                            latitude_val=location.getLatitude();
                            longitude_val=location.getLongitude();
                            //FirebaseDatabase.getInstance().getReference().child("accidentLocation").setValue(locationModel);

                        }
                    }, null);

                } else {
                    ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE);
                }

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.INTERNET}
                                , REQUEST_CODE);
                    }
                    // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            //PARAMETERS ARE THE SERVICE, MIN TIME IN MILLI SEC, MIN DISTANCE, AND LISTENER
            //locationManager.requestLocationUpdates("gps", 0, 0, (android.location.LocationListener) locationListener);

            startPosting();
            }
        });
    }

    private void getloc(Double latitude_val, Double longitude_val) {
        this.latitude_val = latitude_val;
        this.longitude_val = longitude_val;
    }

    //method for posting
    private void startPosting() {
        mprogress.setMessage("Posting");
        mprogress.show();
        final String title_val = mtitleEditText.getText().toString().trim();
        final String desc_val = mdescEditText.getText().toString().trim();
        final String post_id = String.valueOf(System.currentTimeMillis());
        final String user_id = String.valueOf(userid);
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

                               Post post = new Post(post_id,title_val,desc_val,downloaduri,user_id,timestamp,latitude_val,longitude_val);
                               mdatabase.child(post.getPost_id()).setValue(post);

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


    //CHECKING IF LOCATION PERMISSIONS WERE GRANTED
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //submitPostButton();
                }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}

