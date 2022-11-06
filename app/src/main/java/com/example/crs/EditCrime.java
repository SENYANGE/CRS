package com.example.crs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.crs.databinding.ActivityEditCrimeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditCrime extends AppCompatActivity {
    ActivityEditCrimeBinding binding;
    String message_id,comp,police_id,police_push;
    DatabaseReference mDatabase,uMDatabase;
    Dialog edit_dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditCrimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent c=getIntent();

        comp=c.getStringExtra("comp");
        message_id=c.getStringExtra("message_id");
        police_id=c.getStringExtra("police_id");
        police_push=c.getStringExtra("police_meso_id");

        //database ref to police messages
        mDatabase= FirebaseDatabase.getInstance().getReference().child("CRS").child("Messages").child(police_id);//for police node
        //dataref to user messages
        uMDatabase= FirebaseDatabase.getInstance().getReference().child("CRS").child("Messages").child(FirebaseAuth.getInstance().getCurrentUser().getUid());//for user/complainant node
        binding.editComp.setText(comp);
        //on clicking the button dialog shows
        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_dialog = new Dialog(EditCrime.this);
                edit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                edit_dialog.setContentView(R.layout.edit_confirm_dialog);
                edit_dialog.show();

                ImageButton ok=(ImageButton) edit_dialog.findViewById(R.id.ok);
                ImageButton cancle=(ImageButton) edit_dialog.findViewById(R.id.cancle_edit);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase.child(police_push).child("Message").setValue(binding.editComp.getText().toString());
                        uMDatabase.child(message_id).child("Message").setValue(binding.editComp.getText().toString());
                        Toast.makeText(getApplicationContext(),"Edited Successfully",Toast.LENGTH_SHORT).show();
                        edit_dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),MyMessages.class));
                        finish();
                    }
                });
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edit_dialog.dismiss();
                    }
                });

            }
        });
    }
}