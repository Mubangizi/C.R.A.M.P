package com.example.xed.usec;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText mregEmail;
    private EditText mregPassword;
    private EditText mregConfirmPass;
    private Button mregBtn;
    private Button mregloginBtn;
    private ProgressBar mregprogress;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mregBtn = (Button) findViewById(R.id.regbtn);
        mregloginBtn = (Button) findViewById(R.id.regloginbtn);
        mregEmail =(EditText) findViewById(R.id.regemail);
        mregPassword = (EditText) findViewById(R.id.regpassword);
        mregConfirmPass = (EditText) findViewById(R.id.regconfirmpassword);
        mregprogress = findViewById(R.id.regprogress);


        mregBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_val = mregEmail.getText().toString();
                String password_val = mregPassword.getText().toString();
                String confimpass_val =mregConfirmPass.getText().toString();

                if(!TextUtils.isEmpty(email_val) && !TextUtils.isEmpty(password_val) && !TextUtils.isEmpty(confimpass_val)){

                    if(password_val.equals(confimpass_val)){
                        mregprogress.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(email_val,password_val).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){
                                    sendtomain();
                                    toastmessage("Account Successfully created");
                                }else {
                                    String errormessage = task.getException().getMessage();
                                    toastmessage(errormessage);
                                }
                            }
                        });

                        mregprogress.setVisibility(View.INVISIBLE);
                    }else {
                        toastmessage("Password fields dont match");
                    }
                }
            }
        });


        mregloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        //checking if user is logged in
        FirebaseUser currenUser = mAuth.getCurrentUser();
        if (currenUser!=null){
            sendtomain();
        }
    }

    private void sendtomain() {
        Intent mainintent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainintent);
        finish();
    }


    public void  toastmessage(String message){
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
