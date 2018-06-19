package com.example.xed.usec;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar mmainToolBar;
    private FirebaseAuth mAuth;

    private BottomNavigationView mainBottonNav;
    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up the toolbar
        mmainToolBar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mmainToolBar);
        getSupportActionBar().setTitle("Add New Post");

        mAuth=FirebaseAuth.getInstance();
        mainBottonNav = (BottomNavigationView) findViewById(R.id.bottomNavigationmenu) ;

        if(mAuth.getCurrentUser()!= null) {
            //FRAGMENTS
            homeFragment = new HomeFragment();
            notificationFragment = new NotificationFragment();

            //MAKING SURE HOME FRAGMENT IS LOADED FIRST
            replaceFragment(homeFragment);


            mainBottonNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.bottomActionHome:
                            replaceFragment(homeFragment);
                            return true;

                        case R.id.bottomActionNotification:
                            replaceFragment(notificationFragment);

                            return true;

                        default:
                            return false;


                    }

                }
            });
        }
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

            case R.id.actionAccountSettingsbtn:
                Intent accountsetupIntent = new Intent(MainActivity.this, AccoutSetupActivity.class);
                startActivity(accountsetupIntent);
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

    private void replaceFragment(android.support.v4.app.Fragment fragment){
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.maincontainer, fragment);
        fragmentTransaction.commit();
    }
}
