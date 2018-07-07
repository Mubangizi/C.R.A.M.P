package com.example.xed.usec;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
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
    private BottomNavigationView mainbottomnav;
    private FloatingActionButton maddfloat;

    //fragments
    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private MapFragment mapFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        maddfloat = (FloatingActionButton) findViewById(R.id.addfloatingBtn);

        //Setting up the toolbar
        mmainToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mmainToolBar);
        getSupportActionBar().setTitle("Crime reports");

        mainbottomnav = findViewById(R.id.mainbottomNavView);

        //FRAGMENTS
        homeFragment =new HomeFragment();
        notificationFragment= new NotificationFragment();
        mapFragment = new  MapFragment();

        //MAKING SURE HOME FRAGMENT IS LOADED FIRST
        replaceFragment(homeFragment);


        mainbottomnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.actionHomeBottomNav:
                        replaceFragment(homeFragment);
                        return true;

                    case R.id.actionNotBottomNav:
                        replaceFragment(notificationFragment);
                        return true;

                    case R.id.actionMapBottomNav:
                        replaceFragment(mapFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });


        maddfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postintent = new Intent(MainActivity.this,PostActivity.class);
                startActivity(postintent);
            }
        });

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

        switch (item.getItemId()) {
            case R.id.actionlogoutbtn:
                logout();
                return true;

            case R.id.actionAccountSettingsbtn:
                Intent accsetupintent = new Intent(MainActivity.this,AccountSetupActivity.class);
                startActivity(accsetupintent);
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

    //METHOD FOR REPLACING FRAGMENTS

    public  void replaceFragment(android.support.v4.app.Fragment fragment){
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }
}
