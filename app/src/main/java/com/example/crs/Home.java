package com.example.crs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.crs.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Home extends AppCompatActivity {
private  FirebaseAuth mAuth;
private ActivityHomeBinding binding;
private String currentuserid;
private DatabaseReference reference;
boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            currentuserid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("CRS");
        reference.keepSynced(true);
        //adding prof pic and contact to home page
        reference.child("users").child(currentuserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {

                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("image")))) {
                    String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                    String retrievesStatus = dataSnapshot.child("status").getValue().toString();
                    String phone=dataSnapshot.child("phone").getValue().toString();
                    String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();

                    binding.name.setText(retrieveUserName);
                    binding.contact.setText(retrievesStatus);
                    binding.contact.setText(phone);
                    if (retrieveProfileImage.equalsIgnoreCase("default")) {
                        Picasso.get().load(R.drawable.profile1).into(binding.profPic);
                    } else {
                        Picasso.get().load(retrieveProfileImage).into(binding.profPic);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        binding.sendCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crime = new Intent(Home.this, PoliceStations.class);
                //crime.putExtra("crime",binding.crimeD.getText().toString());
                startActivity(crime);
            }
        });
        binding.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Profile.class));
            }
        });
        binding.myComplaintsSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,MyMessages.class));
            }
        });
//location gps
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS Location is Off!");
            builder.setMessage("Please enable Location ");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
        if (mAuth.getCurrentUser() != null) {
            checkonlineornot("Online");
        } else {
            checkonlineornot("Offline");
        }

    }else {
            startActivity(new Intent(Home.this,LoginPage.class));
            finish();
        }

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

        String Current_userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference.child("users").child(Current_userid).child("userstate").setValue(hashMap);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();

        if(firebaseUser !=null) {
            checkonlineornot("offline");
        }

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.logout:
                AlertDialog alertDialog = new AlertDialog.Builder(Home.this)
//set icon
                        .setIcon(R.drawable.crs)
//set title
                        .setTitle("Are you sure to Logout")
//set message
                        .setMessage("LogOut CRS User!")
//set positive button
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                checkonlineornot("offline");
                                startActivity(new Intent(Home.this,LoginPage.class));
                                FirebaseAuth.getInstance().signOut();
                                finish();
                            }
                        })
//set negative button
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                //Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
                return true;
            case R.id.settings:
                startActivity(new Intent(Home.this, SettingsPage.class));
                return  true;
            //case R.id.profile:
              //  startActivity(new Intent(Home.this,Profile.class));
              //  return  true;
           // case R.id.complaints:
                //startActivity(new Intent(Home.this,MyReportedComplaints.class));
               // startActivity(new Intent(Home.this,MyMessages.class));
              //  return  true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}