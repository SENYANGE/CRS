package com.example.crs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.crs.constructors.User;
import com.example.crs.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
private ActivityRegisterBinding binding;
private EditText emailTextView, passwordTextView,passwordReText,fullName,phon;
private ProgressBar progressbar;
private FirebaseAuth mAuth;
private DatabaseReference mDatabase;
private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("CRS");

        // initialising all views through id defined above
        emailTextView = binding.etEmail;
        passwordTextView =binding.etPassword;
        passwordReText=binding.etRepassword;
        fullName=binding.etName;
        phon=binding.editTextPhone;
        progressbar = binding.progressbar;
        //go back to login page
        binding.swipeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,LoginPage.class));
            }
        });
        //register btn
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });

    }
    private void registerNewUser() {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        String email, password,re_password,username,phone;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();
        re_password = passwordReText.getText().toString();
        username = fullName.getText().toString();
        phone=phon.getText().toString();


        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            progressbar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(getApplicationContext(),
                            "Please enter Phone!!",
                            Toast.LENGTH_LONG)
                    .show();
            progressbar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            progressbar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter Full Name!",
                    Toast.LENGTH_LONG)
                    .show();
            progressbar.setVisibility(View.GONE);
            return;
        }
        if(!(password.equalsIgnoreCase(re_password))){
            Toast.makeText(getApplicationContext(),"Password Missmatch!",Toast.LENGTH_LONG).show();
            progressbar.setVisibility(View.GONE);
            return;
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUser=mAuth.getCurrentUser();
                        //creating new user
                            User user=new User(mUser.getUid(),email,phone,fullName.getText().toString(),String.valueOf(0.0),String.valueOf(0.0),"default","Public police","victim");
                            mDatabase.child("users").child(mUser.getUid()).setValue(user);
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);

                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(Register.this,
                                    LoginPage.class);
                            startActivity(intent);
                        } else {

                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
    }

}