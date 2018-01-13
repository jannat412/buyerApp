package com.ingeniumbd.buyerapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ingeniumbd.buyerapp.Manifest;
import com.ingeniumbd.buyerapp.R;

public class LoginActivity extends AppCompatActivity {

    /**
     *-------xml instance------------
     **/
    private EditText userEmailEt,userPasswordEt;


    /**
     *---------class instance----------
     **/
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        };

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Attempting to login...");
        progressDialog.setCancelable(false);


        /**
         *--------xml initialization-------
         **/
        userEmailEt = findViewById(R.id.login_activity_userName);
        userPasswordEt = findViewById(R.id.login_activity_password);
    }

    /**
     *---------user login here--------
     **/
    public void login(View view) {
        String email = userEmailEt.getText().toString();
        String password = userPasswordEt.getText().toString();

        if (email.equals("")){
            userEmailEt.setError("please put your email");
        }
        if (password.equals("")){
            userPasswordEt.setError("Please put your password");
        }


        if (!email.equals("") && !password.equals("")){
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth != null){
            firebaseAuth.addAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (firebaseAuth != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    public void signUp(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }




}
