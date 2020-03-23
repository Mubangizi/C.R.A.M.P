package com.example.xed.usec;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView postlistView;
    private List <Post> mpostlist;
    private DatabaseReference myRef;
    private DatabaseReference mdatabaseUsers;
    private PostRecyclerAdapter postrecycler;
    private LinearLayoutManager linearLayoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        myRef = FirebaseDatabase.getInstance().getReference("Posts");
        mdatabaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        mdatabaseUsers.keepSynced(true);
        myRef.keepSynced(true);
        mpostlist = new ArrayList<>();

        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        postlistView = view.findViewById(R.id.post_list_view);
        postlistView.setHasFixedSize(true);
        postlistView.setLayoutManager(linearLayoutManager);
        postlistView.setItemAnimator(new DefaultItemAnimator());


        postrecycler = new PostRecyclerAdapter(mpostlist);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getFirebaseData();
        postlistView.setAdapter(postrecycler);
    }

    public void getFirebaseData(){

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Post post=dataSnapshot.getValue(Post.class);

                if(post != null){
                    mpostlist.add(post);
                    postrecycler.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private class PostViewHolder extends RecyclerView.ViewHolder {

        View mview;
        public PostViewHolder(View itemView) {
            super(itemView);

            mview =itemView;
        }
    }
}
