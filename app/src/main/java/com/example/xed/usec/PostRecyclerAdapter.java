package com.example.xed.usec;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostRecyclerAdapter extends RecyclerView.Adapter <PostRecyclerAdapter.Viewholder> {

    private List<Post> mpostlist;
    private Context context;
    private DatabaseReference mdatabase;

    PostRecyclerAdapter(List<Post> mpostlist){
        this.mpostlist= mpostlist;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post_list_layout,parent,false);
        context = parent.getContext();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position) {


        String titledata =  mpostlist.get(position).getTitle();
        holder.setTitleText(titledata);

        String descdata =  mpostlist.get(position).getDesc();
        holder.setDescText(descdata);

        String image_uri = mpostlist.get(position).getImage();
        holder.setimageview(image_uri);

        Long timestamp = mpostlist.get(position).getTimestamp();
        holder.setdateView(timestamp);

        final String user_id = mpostlist.get(position).getUser_id();

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String username = String.valueOf(dataSnapshot.child(user_id).child("username").getValue());
                String image = String.valueOf(dataSnapshot.child(user_id).child("profileImage").getValue());
              holder.setUserdata(username,image);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //GETS NO. OF ITEMS TO POPULATE IN THE RECYCLER
    @Override
    public int getItemCount() {
        return mpostlist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        View mview;
        TextView titleView;
        TextView descView;
        TextView dateView;
        ImageView postimageView;
        TextView usernameView;
        CircleImageView profileView;

        public Viewholder(View itemView) {
            super(itemView);
            mview=itemView;

        }

        public void setTitleText(String titleval) {
            titleView = mview.findViewById(R.id.postitle);
            titleView.setText(titleval);
        }

        public void setDescText(String descval) {
            descView = mview.findViewById(R.id.postdesc);
            descView.setText(descval);
        }

        public  void setimageview(String downloadUri){
            postimageView =mview.findViewById(R.id.postimage);
            Glide.with(context).load(downloadUri).into(postimageView);
        }

        public void  setdateView(Long timestamp){
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setTimeInMillis(timestamp);
            String date = String.valueOf(DateFormat.format("dd/MMM/yyyy hh:mm",cal));
            dateView = mview.findViewById(R.id.postdate);
            dateView.setText(date);
        }


        public void setUserdata(String name, String profileimage) {
            usernameView = mview.findViewById(R.id.postUsername);
            profileView = mview.findViewById(R.id.postuserimage);
            usernameView.setText(name);

            RequestOptions placeHolderOptions =new RequestOptions();
            placeHolderOptions.placeholder(R.mipmap.action_account);
            Glide.with(context).applyDefaultRequestOptions(placeHolderOptions).load(profileimage).into(profileView);
        }
    }
}