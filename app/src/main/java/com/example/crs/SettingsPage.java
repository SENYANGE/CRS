package com.example.crs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsPage extends AppCompatActivity {
    EditText user_name,description;
    Button btn_update;
    ImageButton camera;
    private CircleImageView circleImageView1;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private String currentuserid;
    private Toolbar mToolbar;

    private StorageReference Storage2;
    private static final int bic=1;

    private static final int GalleryPick = 1;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    ProgressDialog progressDialog;
    String cameraPermission[];
    String storagePermission[];
    Uri imageuri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        user_name=(EditText)findViewById(R.id.username);
        description=(EditText)findViewById(R.id.status);
        btn_update=(Button) findViewById(R.id.update_btn);
        camera=findViewById(R.id.camera_btn);
        circleImageView1=(CircleImageView)findViewById(R.id.profile_image1);
        Storage2= FirebaseStorage.getInstance().getReference().child("image");
        auth=FirebaseAuth.getInstance();
        currentuserid=auth.getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference().child("CRS");
        reference.keepSynced(true);
        progressDialog = new ProgressDialog(SettingsPage.this);
        progressDialog.setMessage("UpLoading  Please Wait...");


        // allowing permissions of gallery and camera
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

camera.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showImagePicDialog();
    }
});
        RetrieveUserInfo();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateyourinfo();

            }
        });


    }

    private void showImagePicDialog() {
        String options[] = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromGallery();
                    }
                } else if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }
    // checking storage permissions
    private Boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    // Requesting  gallery permission
    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(storagePermission, STORAGE_REQUEST);
        }
    }

    // checking camera permissions
    private Boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    // Requesting camera permission
    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            requestPermissions(cameraPermission, CAMERA_REQUEST);
        }
    }
    // Requesting camera and gallery
    // permission if not given
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accepted && writeStorageaccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please Enable Camera and Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST: {
                if (grantResults.length > 0) {
                    boolean writeStorageaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageaccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please Enable Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }
    }

    // Here we will pick image from gallery or camera
    private void pickFromGallery() {
        CropImage.activity().start(SettingsPage.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
               // Picasso.with(this).load(resultUri).into(userpic);
                progressDialog.show();
                StorageReference filePath = Storage2.child(currentuserid + ".jpg");


                filePath.putFile(resultUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                                firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Toast.makeText(SettingsPage.this, "Profile Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                                        final String downloaedUrl =   uri.toString();




                                        reference.child("users").child(currentuserid).child("image")
                                                .setValue(downloaedUrl)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task)
                                                    {
                                                        if (task.isSuccessful())
                                                        {
                                                            //Toast.makeText(SettingsPage.this, "Image save in Database, Successfully...", Toast.LENGTH_SHORT).show();
                                                            progressDialog.dismiss();
                                                        }
                                                        else
                                                        {
                                                            String message = task.getException().toString();
                                                            Toast.makeText(SettingsPage.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                                                        }
                                                    }

                                                });

                                    }
                                });


                            }


                        });


            }
        }
    }




    private void updateyourinfo(){
        String name=user_name.getText().toString();
        String status=description.getText().toString();



        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "enter your name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(status)){
            Toast.makeText(this, "enter your Status", Toast.LENGTH_SHORT).show();
        }
        else{
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("user_Id",currentuserid);
            hashMap.put("name",name);
            hashMap.put("status",status);


            reference.child("users").child(currentuserid).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SettingsPage.this, "Successfull", Toast.LENGTH_SHORT).show();
                        sendusertoMain();
                    }
                    else{
                        String message=task.getException().toString();
                        Toast.makeText(SettingsPage.this, "Error..."+message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void sendusertoMain() {
        Intent mainIntent = new Intent(SettingsPage.this, Home.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void RetrieveUserInfo()
    {
        reference.child("users").child(currentuserid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("image"))))
                        {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrievesStatus = dataSnapshot.child("status").getValue().toString();
                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();

                            user_name.setText(retrieveUserName);
                            description.setText(retrievesStatus);
                            if(retrieveProfileImage.equalsIgnoreCase("default")){
                                Picasso.get().load(R.drawable.profile1).into(circleImageView1);
                            }else
                            {
                                Picasso.get().load(retrieveProfileImage).into(circleImageView1);
                            }
                        }
                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")))
                        {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrievesStatus = dataSnapshot.child("status").getValue().toString();

                            user_name.setText(retrieveUserName);
                            description.setText(retrievesStatus);
                        }
                        else
                        {
                            user_name.setVisibility(View.VISIBLE);
                            Toast.makeText(SettingsPage.this, "Please set & update your profile information...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

}