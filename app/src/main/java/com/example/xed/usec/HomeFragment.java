package com.example.xed.usec;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mpostView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mpostView = getActivity().findViewById(R.id.postlistView);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}