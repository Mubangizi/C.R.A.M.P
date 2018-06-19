package com.example.xed.usec;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mpostView;
    private List<Post> post_list;
    private FirebaseFirestore firebaseFirestore;
    private PostRecyclerAdapter postRecyclerAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mpostView = getActivity().findViewById(R.id.postlistView);
        post_list = new ArrayList<>();
        postRecyclerAdapter = new PostRecyclerAdapter(post_list);

        /*
        //SETTING THE ADAPTER
        mpostView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mpostView.setAdapter(postRecyclerAdapter);

        //Retrieving the data
        firebaseFirestore=FirebaseFirestore.getInstance();


        //posts is the folder on firestore we want to retrieve data from
        firebaseFirestore.collection("Posts").addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                //LOOP TO CHECK DOCUMENT CHANGES
                for (DocumentChange doc: documentSnapshots.getDocumentChanges()){

                    if(doc.getType()==DocumentChange.Type.ADDED){
                        //INCASE OF NEW CHANGES
                        Post post = doc.getDocument().toObject(Post.class);
                        post_list.add(post);

                        //NOW TO PUT THEM IN THE RECYCLER VIEW
                        postRecyclerAdapter.notifyDataSetChanged();

                    }
                }

            }
        });

            */

        // Inflate the layout for this fragment
        return view;
    }

}
