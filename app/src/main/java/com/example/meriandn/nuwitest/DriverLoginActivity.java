package com.example.meriandn.nuwitest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverLoginActivity extends AppCompatActivity {

    private Button mLogin, mRegister;
    private TextView mEmail, mPassword, mLink;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        mLogin =(Button) findViewById(R.id.login);
        mRegister=(Button)findViewById(R.id.register);
        mEmail= (TextView) findViewById(R.id.email);
        mPassword =(TextView)findViewById(R.id.password);
        mLink =(TextView) findViewById(R.id.link);
        loadingBar = new ProgressDialog(this);

        mRegister.setVisibility(View.INVISIBLE);
        mRegister.setEnabled(false);

        mLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogin.setVisibility(View.INVISIBLE);
                mLink.setVisibility(View.INVISIBLE);

                mRegister.setVisibility(View.VISIBLE);
                mRegister.setEnabled(true);
            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                RegisterCustomer(email, password);
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                LoginCustomer(email, password);

            }
        });


    }

    private void LoginCustomer(String email, String password) {


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(DriverLoginActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(DriverLoginActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Drivers Login");
            loadingBar.setMessage("please wait, we are checking your credientials..");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(DriverLoginActivity.this, "Driver Login Successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Intent intent = new Intent(DriverLoginActivity.this, DriverMapActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DriverLoginActivity.this, "Driver Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }

    }



    private void RegisterCustomer(String email, String password) {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLoginActivity.this,"Please enter Email",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLoginActivity.this,"Please enter Password",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Drivers Registration");
            loadingBar.setMessage("Please wait, we are register Your information");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(DriverLoginActivity.this,"Driver Register Successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Intent intent = new Intent(DriverLoginActivity.this, DriverMapActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(DriverLoginActivity.this,"Driver Registration Unsuccessful",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            }) ;
        }
    }
}