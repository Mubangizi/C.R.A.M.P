package com.example.xed.usec;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.Date;
import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.Viewholder>{

    private List<Post> post_list;
    private Context context;

    //constructer taking in a list
    public  PostRecyclerAdapter(List<Post> post_list){
        this.post_list=post_list;
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        private View mview;
        private TextView mtitletextview;
        private TextView mdesctextview;
        private ImageView mpostImageView;
        private TextView mpostdate;

        public Viewholder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setTitleText(String titleText){
            mtitletextview =mview.findViewById(R.id.postitle);
            mtitletextview.setText(titleText);

        }
        public void setdescText(String desctext){
            mdesctextview = mview.findViewById(R.id.postdesc);
            mdesctextview.setText(desctext);
        }

        public void setImageText(String downloadUri){
            mpostImageView = mview.findViewById(R.id.post_image);
            Glide.with(context).load(downloadUri).into(mpostImageView);
        }

        public void setdatetime(String postdate){
            mpostdate = mview.findViewById(R.id.postdate);
            mpostdate.setText(postdate);

        }

    }


    @NonNull
    @Override
    public PostRecyclerAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postlistlayout, parent, false);
        context = parent.getContext();

        return new Viewholder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        String title_data =post_list.get(position).getTitle();
        holder.setImageText(title_data);

        String desc_data = post_list.get(position).getDesc();
        holder.setdescText(desc_data);

        String imageUrl =  post_list.get(position).getImage();
        holder.setImageText(imageUrl);

        long milliseconds =post_list.get(position).getTimestamp().getTime();        //CONVERTS TIMESTAMP TO LONG VALUE OF TIME
        String dateString = DateFormat.format("MM/DD/YYYY", new Date(milliseconds)).toString();
        holder.setdatetime(dateString);
    }


    @Override
    public int getItemCount() {
        return post_list.size();
    }
}
