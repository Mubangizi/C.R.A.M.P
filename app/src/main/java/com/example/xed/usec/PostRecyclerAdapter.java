package com.example.xed.usec;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter <PostRecyclerAdapter.Viewholder> {

    private List<Post> mpostlist;
    private Context context;
    Post post = new Post();

    PostRecyclerAdapter(List<Post> mpostlist){
        this.mpostlist= mpostlist;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post_list_layout,parent,false);

        context = parent.getContext();
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {


        String titledata =  mpostlist.get(position).getTitle();
        holder.setTitleText(titledata);

        String descdata =  mpostlist.get(position).getDesc();
        holder.setDescText(descdata);

        String image_uri = mpostlist.get(position).getImage();
        holder.setimageview(image_uri);


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
        ImageView postimageView;
        TextView userView;

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

        public void setUserText(String userVal) {
            userView = mview.findViewById(R.id.postUsername);
            userView.setText(userVal);
        }
    }
}