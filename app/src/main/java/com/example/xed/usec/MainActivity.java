package com.example.xed.usec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar mmainToolBar;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up the toolbar
        mmainToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mmainToolBar);
        getSupportActionBar().setTitle("Crime reports");


        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //checking if logged in
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentuser==null){

            sendtoLogin();
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

        switch (item.getItemId()) {
            case R.id.actionAdd:
                Intent postintent = new Intent(MainActivity.this,PostActivity.class);
                startActivity(postintent);
                return true;

            case R.id.actionlogoutbtn:
                logout();
                return true;

            default:
                    return false;   //previous return; return super.onOptionsItemSelected(item);
        }
    }

    private void sendtoLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void logout() {
        mAuth.signOut();
        sendtoLogin();
    }
}
