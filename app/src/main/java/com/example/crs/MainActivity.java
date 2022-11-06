package com.example.crs;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import com.example.crs.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

   // private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    Handler handler;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("CRS");
        reference.keepSynced(true);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mUser!=null){

                    reference.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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

/**

                    Intent intent=new Intent(MainActivity.this,Home.class);
                    startActivity(intent);
                    finish();//ends the activity(doesn't show the mainActivity page on back press
                    **/

                }else{
                    Intent intent=new Intent(MainActivity.this,LoginPage.class);
                    startActivity(intent);
                    finish();
                }

            }
        },3000);

    }
    private void checkonlineornot(String State)
    {

        String Savecurrenttime,SaveDate;
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM,dd - yyyy");
        SaveDate=dateFormat.format(calendar.getTime());

        SimpleDateFormat dateFormat1=new SimpleDateFormat("hh:mm a");
        Savecurrenttime=dateFormat1.format(calendar.getTime());

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("date",SaveDate);
        hashMap.put("Time",Savecurrenttime);
        hashMap.put("State",State);

        String Current_userid=mAuth.getCurrentUser().getUid();
        reference.child("users").child(Current_userid).child("userstate").updateChildren(hashMap);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();


        if(firebaseUser ==null)
        {
            startActivity(new Intent(MainActivity.this,LoginPage.class));
            finish();
        }

        else {
            checkonlineornot("Online");
            String currentid=mAuth.getCurrentUser().getUid();
            reference.child("users").child(currentid).addListenerForSingleValueEvent(new ValueEventListener() {
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
        }
    }
}