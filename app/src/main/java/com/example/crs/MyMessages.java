package com.example.crs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crs.constructors.Messages;
import com.example.crs.constructors.MyReceivedComplaintsAdapter;
import com.example.crs.constructors.MySentComplaintsAdapter;
import com.example.crs.constructors.globals;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyMessages extends AppCompatActivity {
    private RecyclerView recyclerView_chat;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    String current_user_id,mes_id;
    DatabaseReference reference_chat,mDatabase,reference;
    String date=" " ;
    String time="";
    Dialog witness_dialog,confirm;
    String user_id;
    String reciever_id;
    List<Messages> list = new ArrayList<>();
    DividerItemDecoration itemDecorator;
    String pusk_key;
    static String comp_id;
    MySentComplaintsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);

        recyclerView_chat=findViewById(R.id.mrec_chat);
        progressDialog = new ProgressDialog(MyMessages.this);
        progressDialog.setMessage("Loading Complaints Please Wait...");
        progressDialog.show();
        auth=FirebaseAuth.getInstance();
        current_user_id=auth.getCurrentUser().getUid();
        reference_chat= FirebaseDatabase.getInstance().getReference().child("CRS");
        user_id=auth.getCurrentUser().getUid();

        recyclerView_chat.setHasFixedSize(true);


        // Setting RecyclerView layout as LinearLayout.
        recyclerView_chat.setLayoutManager(new LinearLayoutManager(MyMessages.this));

        mes_id=reference_chat.child("Messages").push().getKey();

        mDatabase=reference_chat.child("Messages").child(user_id);//.child(mes_id);//all messages
        reference=reference_chat.child("Messages");//for police officer
       //reference_user= FirebaseDatabase.getInstance().getReference().child("CRS").child("users");
        mDatabase.keepSynced(true);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Messages imageUploadInfo = postSnapshot.getValue(Messages.class);

                    list.add(imageUploadInfo);
                }
                adapter=new MySentComplaintsAdapter(MyMessages.this, list, new MySentComplaintsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Messages item) {
                        Toast.makeText(getApplicationContext(),item.getMessage(),Toast.LENGTH_SHORT).show();
                        witness_dialog = new Dialog(MyMessages.this);
                        witness_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        witness_dialog.setContentView(R.layout.witness_dialog);
                        witness_dialog.show();


                        TextView cx_message = (TextView) witness_dialog .findViewById(R.id.c_message);
                        ImageButton c_edit=(ImageButton) witness_dialog.findViewById(R.id.btn_edit);
                        ImageButton c_delete=(ImageButton)witness_dialog.findViewById(R.id.btn_delete);

                        cx_message.setText(item.getMessage());

                        c_edit.setOnClickListener(new View.OnClickListener() {
                            String key;
                            @Override
                            public void onClick(View v) {
                                Query ref=mDatabase.orderByChild("Message").equalTo(item.getMessage());
                                //police id
                                Query police_ref=reference.child(item.getReceiver()).orderByChild("Message").equalTo(item.getMessage());
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot child:snapshot.getChildren()){
                                            pusk_key=child.getKey();
                                        }
                                        police_ref.addListenerForSingleValueEvent(new ValueEventListener() {


                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for(DataSnapshot child:snapshot.getChildren()){
                                                    comp_id=child.getKey();
                                                    globals.comp_id=comp_id;

                                                }



                                                // Toast.makeText(getApplicationContext(),comp_id,Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        Intent   c= new Intent(MyMessages.this,EditCrime.class);//edit intent;
                                        c.putExtra("message_id",pusk_key);//user message sent
                                        c.putExtra("comp",item.getMessage());
                                        c.putExtra("police_id",item.getReceiver());
                                        c.putExtra("police_meso_id",globals.comp_id);





                                        witness_dialog.dismiss();

                                        startActivity(c);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                            }
                        });
                        c_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Query ref=mDatabase.orderByChild("Message").equalTo(item.getMessage());
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot child:snapshot.getChildren()) {

                                            pusk_key=child.getKey();
                                        }
                                        witness_dialog.dismiss();

                                        AlertDialog alertDialog = new AlertDialog.Builder(MyMessages.this)
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

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                               // Toast.makeText(getApplicationContext(), pusk_key, Toast.LENGTH_SHORT).show();
                                witness_dialog.dismiss();
                            }
                        });




                    }
                });
                adapter.notifyDataSetChanged();
                itemDecorator = new DividerItemDecoration(MyMessages.this, DividerItemDecoration.VERTICAL);
                itemDecorator.setDrawable(ContextCompat.getDrawable(MyMessages.this, R.drawable.divider));

                recyclerView_chat.addItemDecoration(itemDecorator);

                recyclerView_chat.setAdapter(adapter);


                // Hiding the progress dialog.
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }




}