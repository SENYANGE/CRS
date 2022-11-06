package com.example.crs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.crs.constructors.Messages;
import com.example.crs.constructors.MyReceivedComplaintsAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.record.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

public class RecievedComplaints extends AppCompatActivity {
    private RecyclerView recyclerView_chat;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
     String current_user_id,mes_id,messages_id;
     String reciever_id="default";
    private String user_Id,longitude,latitude,phone_number;
    String userState,username,userImage;

    // Creating List of ImageUploadInfo class.
    List<Messages> list = new ArrayList<>();
    DividerItemDecoration itemDecorator;
    String pusk_key;
    boolean seen;
    MyReceivedComplaintsAdapter adapter;
    private String pushKey;


    DatabaseReference reference_chat,mDatabase,reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieved_complaints);

//recyclerview
        recyclerView_chat=findViewById(R.id.recieved_comp);
//progressbar
        progressDialog = new ProgressDialog(RecievedComplaints.this);
        progressDialog.setMessage("Loading Complaints Please Wait...");
        progressDialog.show();
        auth=FirebaseAuth.getInstance();
        current_user_id=auth.getCurrentUser().getUid();
        //mes_id=reference_chat.child("Messages").push().getKey();
       recyclerView_chat.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView_chat.setLayoutManager(new LinearLayoutManager(RecievedComplaints.this));

        mDatabase=FirebaseDatabase.getInstance().getReference().child("CRS").child("Messages").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //reference_user= FirebaseDatabase.getInstance().getReference().child("CRS").child("users");
        mDatabase.keepSynced(true);
        //adding listerner to mDatabase

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Messages imageUploadInfo = postSnapshot.getValue(Messages.class);

                    list.add(imageUploadInfo);
                }
                adapter=new MyReceivedComplaintsAdapter(RecievedComplaints.this, list, new MyReceivedComplaintsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Messages item) {
                       //delete item from view
                        Query ref=mDatabase.orderByChild("Message").equalTo(item.getMessage());
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot child:snapshot.getChildren()){
                                    pusk_key= child.getKey();
                                    seen=child.child("seen").getValue(Boolean.class);
                                }
                                if(seen==true){
                                    AlertDialog alertDialog = new AlertDialog.Builder(RecievedComplaints.this)
//set icon
                                            .setIcon(R.drawable.crs)
//set title
                                            .setTitle("Delete? "+" "+item.getMessage())
//set message
                                            .setMessage("Are you sure!")
//set positive button
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    mDatabase.child(pusk_key).removeValue();
                                                    recreate();//rebuilds activity as a way of refreshing it
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


                                }else{
                                    Toast.makeText(getApplicationContext(),"Officer you can not delete Complaint without checking it out", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                }, new MyReceivedComplaintsAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(Messages item) {
                        String PhoneNumber = item.getPhone();
                        // Toast.makeText(getApplicationContext(),item.getPhone(),Toast.LENGTH_SHORT).show();
                        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(RecievedComplaints.this)
//set icon
                                .setIcon(R.drawable.crs)
//set title
                                .setTitle("CSR CALL")
//set message
                                .setMessage("Call Complainant" + PhoneNumber + "?")
//set positive button
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        //calling complainant
                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        intent.setData(Uri.parse("tel:" + PhoneNumber));
                                        // Here, thisActivity is the current activity
                                        if (ContextCompat.checkSelfPermission(RecievedComplaints.this,
                                                Manifest.permission.CALL_PHONE)
                                                != PackageManager.PERMISSION_GRANTED) {

                                            ActivityCompat.requestPermissions(RecievedComplaints.this,
                                                    new String[]{Manifest.permission.CALL_PHONE}, 1);

                                            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                                            // app-defined int constant. The callback method gets the
                                            // result of the request.
                                        } else {
                                            //You already have permission
                                            try {
                                                startActivity(intent);
                                                //update compalint seen to true
                                                Query ref1=mDatabase.orderByChild("Message").equalTo(item.getMessage());
                                                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for(DataSnapshot child:snapshot.getChildren()){
                                                            pushKey=child.getKey();

                                                        }
                                                        mDatabase.child(pusk_key).child("seen").setValue(true);//message seen
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            } catch (SecurityException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                })
//set negative button
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //alertDialog.dismiss();
                                    }
                                })
                                .show();
                    }
                });

                adapter.notifyDataSetChanged();
                itemDecorator = new DividerItemDecoration(RecievedComplaints.this, DividerItemDecoration.VERTICAL);
                itemDecorator.setDrawable(ContextCompat.getDrawable(RecievedComplaints.this, R.drawable.divider));

                recyclerView_chat.addItemDecoration(itemDecorator);

                recyclerView_chat.setAdapter(adapter);


                // Hiding the progress dialog.
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

}