package com.example.crs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.crs.databinding.ActivityLoginPageBinding;
import com.example.crs.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class LoginPage extends AppCompatActivity {
   private ActivityLoginPageBinding binding;
   private FirebaseUser mUser;
   private FirebaseAuth mAuth;
    private ProgressBar progressbar;
    private DatabaseReference reference;
    private String user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("CRS");
        reference.keepSynced(true);
        progressbar=binding.loginP;


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            login_user();
               // Toast.makeText(LoginPage.this,"Clicked!",Toast.LENGTH_SHORT).show();

            }
        });
        binding.swipeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this,Register.class));
                finish();
            }
        });
    }

    public void login_user(){
        String email=binding.etEmail.getText().toString();
        String password=binding.etPassword.getText().toString();
        progressbar.setVisibility(View.VISIBLE);
        //validating input
        if(TextUtils.isEmpty(email)){
            binding.etEmail.setError("Enter Email Please");
            progressbar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(password)){
            binding.etPassword.setError("Enter Password");
            progressbar.setVisibility(View.GONE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    reference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("tag").getValue().toString().equalsIgnoreCase("victim")){
                                startActivity(new Intent(getApplicationContext(),Home.class));
                                finish();
                            }else{

                                startActivity(new Intent(getApplicationContext(),PoliceHome.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    progressbar.setVisibility(View.GONE);
                }else{
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Check Network and Try again please!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}