package com.example.crs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.crs.databinding.ActivityPoliceHomeBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class PoliceHome extends AppCompatActivity {
    private  FirebaseAuth mAuth;
    private ActivityPoliceHomeBinding binding;
    private String currentuserid;
    private DatabaseReference reference,userRef,ComplaintRef;
    PieChart pieChart;
    PieData pieData;
    public static int comp;
    public static int seen_comp;
    public static  int user_num;
    boolean doubleBackToExitPressedOnce = false;

    List<PieEntry> pieEntryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_home);
        binding = ActivityPoliceHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //authentication
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            currentuserid = mAuth.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("CRS");
        reference.keepSynced(true);
        //adding prof pic and contact to home page
        reference.child("users").child(currentuserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {

                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("image")))) {
                    String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                    String retrievesStatus = dataSnapshot.child("status").getValue().toString();
                    String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();
                    String phone=dataSnapshot.child("phone").getValue().toString();

                    binding.policename.setText(retrieveUserName);
                    binding.policecontact.setText(retrievesStatus);
                    binding.policecontact.setText(phone);
                    if (retrieveProfileImage.equalsIgnoreCase("default")) {
                        Picasso.get().load(R.drawable.profile1).into(binding.policeprofPic);
                    } else {
                        Picasso.get().load(retrieveProfileImage).into(binding.policeprofPic);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        //all complainants in the system
            userRef=reference.child("users");
            Query ref=userRef.orderByChild("tag").equalTo("victim");

            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        {
                            user_num = (int) snapshot.getChildrenCount();

                                                  }
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });

        //all complaints
         ComplaintRef=reference.child("Messages").child(currentuserid);
         Query compRef=ComplaintRef.orderByChild("Receiver").equalTo(currentuserid);
         compRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                       comp=(int) snapshot.getChildrenCount();



             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
         //all seen complaints
         Query seenCompRef=ComplaintRef.orderByChild("seen").equalTo(true);
         seenCompRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                       seen_comp=(int) snapshot.getChildrenCount();


             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
           // recreate();
            //piechart
            pieChart = binding.myPieChart;
            pieChart.setUsePercentValues(true);
            pieChart.setCenterText("CRS");
            pieChart.setCenterTextColor(Color.RED);
        //adding piechart data
            pieEntryList.add(new PieEntry(user_num,"Users"));
            pieEntryList.add(new PieEntry(comp,"Complaints"));
            pieEntryList.add(new PieEntry(seen_comp,"Seen"));

            PieDataSet pieDataSet = new PieDataSet(pieEntryList,"Category");
            pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.invalidate();
/**
        binding.respond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("users").child(currentuserid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        // User users=postSnapshot.getValue(User.class);
                        Intent crime = new Intent(PoliceHome.this, ReportedComplaints.class);
                        crime.putExtra("userid", snapshot.child("user_Id").getValue().toString());
                        crime.putExtra("name_mm", snapshot.child("name").getValue().toString());
                        crime.putExtra("image", snapshot.child("image").getValue().toString());
                        startActivity(crime);

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


            }
        });
        **/
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

    }else{
            //when no user is logged in
            startActivity(new Intent(PoliceHome.this,LoginPage.class));
            finish();
        }

    }

    private void checkonlineornot(String State) {

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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.policeoptions, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.plogout:
                AlertDialog alertDialog = new AlertDialog.Builder(PoliceHome.this)
//set icon
                        .setIcon(R.drawable.crs)
//set title
                        .setTitle("Are you sure to Logout")
//set message
                        .setMessage("LogOut CRS Officer!")
//set positive button
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                checkonlineornot("offline");
                                startActivity(new Intent(PoliceHome.this,LoginPage.class));
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
            case R.id.psettings:
                startActivity(new Intent(PoliceHome.this, SettingsPage.class));
                return  true;
            case R.id.pprofile:
                startActivity(new Intent(PoliceHome.this,Profile.class));
                return  true;
            case R.id.pcomplaints:
               // startActivity(new Intent(PoliceHome.this,MyReportedComplaints.class));

                startActivity(new Intent(PoliceHome.this,RecievedComplaints.class));
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}