package com.example.crs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.crs.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class Profile extends AppCompatActivity {
    private  FirebaseAuth mAuth;
    private ActivityProfileBinding binding;
    private String currentuserid;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth= FirebaseAuth.getInstance();
        currentuserid=mAuth.getCurrentUser().getUid();

        reference= FirebaseDatabase.getInstance().getReference().child("CRS");
        reference.keepSynced(true);
        //adding prof pic and contact to home page
        reference.child("users").child(currentuserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("image"))))
                {
                    String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                    String retrievesStatus = dataSnapshot.child("status").getValue().toString();
                    String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();

                    binding.visitUserName.setText(retrieveUserName);
                    binding.visitProfileStatus.setText(retrievesStatus);
                    if(retrieveProfileImage.equalsIgnoreCase("default")){
                        Picasso.get().load(R.drawable.profile1).into(binding.visitProfileImage);
                    }else
                    {
                        Picasso.get().load(retrieveProfileImage).into(binding.visitProfileImage);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        binding.updateProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, SettingsPage.class));
            }
        });
    }
}