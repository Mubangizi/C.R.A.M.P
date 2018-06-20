package com.example.xed.usec;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText memailText;
    private EditText mpasswordText;
    private Button mloginBtn;
    private Button mregbtn;
    private FirebaseAuth mAuth;
    private ProgressBar mloginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        memailText = (EditText) findViewById(R.id.loginemail);
        mpasswordText= (EditText) findViewById(R.id.loginpassword);
        mloginBtn = (Button) findViewById(R.id.loginbtn);
        mregbtn = (Button) findViewById(R.id.loginregbtn);
        mloginProgress = (ProgressBar) findViewById(R.id.loginprogress);

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_Val = memailText.getText().toString();
                String pass_Val =mpasswordText.getText().toString();

                if(!TextUtils.isEmpty(email_Val ) && !TextUtils.isEmpty(pass_Val)){         //checking if elements are empty
                    mloginProgress.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email_Val,pass_Val).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent setupIntent = new Intent(LoginActivity.this, AccountSetupActivity.class);
                                startActivity(setupIntent);
                                finish();
                            }else {
                                String errormessage = task.getException().getMessage();
                                toastmessage(errormessage);
                            }
                        }
                    });

                    mloginProgress.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }

    }

    //toastin message
    public  void toastmessage(String message){
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
