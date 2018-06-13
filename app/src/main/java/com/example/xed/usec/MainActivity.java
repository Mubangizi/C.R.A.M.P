package com.example.xed.usec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mpostRecylerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mpostRecylerView = (RecyclerView) findViewById(R.id.post_list);
        mpostRecylerView.setHasFixedSize(true);
        mpostRecylerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    public static class PostViewHolder extends RecyclerView.ViewHolder{
        View mview;
        public PostViewHolder(View itemView) {
            super(itemView);
            itemView =mview;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.actionAdd ){

            Intent postintent = new Intent(MainActivity.this,PostActivity.class);
            startActivity(postintent);

        }

        return super.onOptionsItemSelected(item);
    }
}
