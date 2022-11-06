package com.example.crs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static java.security.AccessController.getContext;

public class MyReportedComplaints extends AppCompatActivity {
    RecyclerView recyclerView_chat;
    View view;

    DatabaseReference reference_user,reference_contact,reference_chat;

    FirebaseAuth auth;

    String current_user_id,Users_id;
    String userImage="default_image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reported_complaints);

        recyclerView_chat=(RecyclerView)view.findViewById(R.id.rec_chat);

        auth=FirebaseAuth.getInstance();
        current_user_id=auth.getCurrentUser().getUid();
        reference_chat= FirebaseDatabase.getInstance().getReference().child("CRS").child("Contacts").child(current_user_id);
        reference_user= FirebaseDatabase.getInstance().getReference().child("CRS").child("users");


        //recyclerView_chat.setLayoutManager(new LinearLayoutManager(MyReportedComplaints.this));
    }


}