package com.example.xed.usec;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

public class SinglePostActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView postdateView;
    private TextView postTitleView;
    private TextView postDescView;
    private ImageView profilepicView;
    private ImageView postimageView;
    private String post_key;
    private String userid;
    private DatabaseReference mdatabase;
    private DatabaseReference muserdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        //getting the key
        post_key = getIntent().getExtras().getString("post_id");

        usernameTextView = (TextView) findViewById(R.id.singleUsername);
        postdateView = (TextView) findViewById(R.id.singlepostdate);
        postTitleView = (TextView) findViewById(R.id.singlepostitle);
        postDescView = (TextView) findViewById(R.id.singlepostdesc);
        profilepicView = (ImageView) findViewById(R.id.singleuserimage);
        postimageView = (ImageView) findViewById(R.id.singlepostimage);

        mdatabase = FirebaseDatabase.getInstance().getReference();
        muserdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String posttitle_val = dataSnapshot.child("Posts").child(post_key).child("title").getValue().toString();
                String postdesc_val = dataSnapshot.child("Posts").child(post_key).child("desc").getValue().toString();
                String postdate_val= getpostdate((Long) dataSnapshot.child("Posts").child(post_key).child("timestamp").getValue());
                String postimageUrl = dataSnapshot.child("Posts").child(post_key).child("image").getValue().toString();

                userid = dataSnapshot.child("Posts").child(post_key).child("user_id").getValue().toString();
                String username_val = dataSnapshot.child("Users").child(userid).child("username").getValue().toString();
                String profileimage_val =dataSnapshot.child("Users").child(userid).child("profileImage").getValue().toString();

                postTitleView.setText(posttitle_val);
                postDescView.setText(postdesc_val);
                postdateView.setText(postdate_val);
                Glide.with(SinglePostActivity.this).load(postimageUrl).into(postimageView);
                usernameTextView.setText(username_val);
                Glide.with(SinglePostActivity.this).load(profileimage_val).into(profilepicView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public String getpostdate(Long timestamp){
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(timestamp);
        String date = String.valueOf(DateFormat.format("dd/MMM/yyyy hh:mm",cal));
        return  date;
    }
}
