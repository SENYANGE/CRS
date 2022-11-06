package com.example.crs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.crs.constructors.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PoliceStations extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude,longitude;
    private String checker="";

    private DatabaseReference mDatabase;
    // creating array list for adding all our police locations.
    //private ArrayList<User> PoliceMarkers=new ArrayList<>();
    private ArrayList<LatLng> locationArrayList=new ArrayList<>();
    Double lati,longi;

    public void centreMapOnLocation(Location location, String title){

        if(location!= null){
             latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        //adding image to marker
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.crs);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 84, 84, false);

        LatLng userLocation = new LatLng(latitude,longitude);
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).position(userLocation).title(title));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));


        //database
        mDatabase =  FirebaseDatabase.getInstance().getReference().child("CRS").child("users");
        mDatabase= FirebaseDatabase.getInstance().getReference().child("CRS").child("users");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot:snapshot.getChildren()){
                    User users=postSnapshot.getValue(User.class);
                    if(users.getTag().equalsIgnoreCase("police")) {
                        lati = Double.parseDouble(users.getLati());
                        longi = Double.parseDouble(users.getLongi());
                        locationArrayList.add(new LatLng(lati, longi));


                        for (int i = 0; i < locationArrayList.size(); i++) {
                            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(locationArrayList.get(i)).title(users.getName()));

                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        for (int i = 0; i < locationArrayList.size(); i++) {

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(locationArrayList.get(i)).title("Police"));

            // below lin is use to zoom our camera on map.
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

            // below line is use to move our camera to the specific location.
           // mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centreMapOnLocation(lastKnownLocation,"ME");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_stations);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {//remove other markers from google map
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
            //dialog
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
           /**
            final Dialog d = new Dialog(PoliceStations.this);
            d.setContentView(R.layout.notificationoptions);
            ImageButton ok = d.findViewById(R.id.noti_ok);
            ImageButton close = d.findViewById(R.id.noti_close);
            ImageView proof=d.findViewById(R.id.imageView6);
            d.getWindow().setLayout((6 * width)/7, (2* height)/3);
**/
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (!marker.getTitle().equalsIgnoreCase("me")){
                        Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                        //return police user ID for name
                            mDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    for(DataSnapshot postSnapshot:snapshot.getChildren()){
                                        User users=postSnapshot.getValue(User.class);
                                        if(users.getName().equalsIgnoreCase(marker.getTitle())) {

                                            Intent map=new Intent(PoliceStations.this,CrimeComplaint.class);
                                            map.putExtra("userid",users.getUser_Id());
                                            map.putExtra("name_mm",users.getName());
                                            map.putExtra("image",users.getImage());
                                            map.putExtra("longi",String.valueOf(longi));
                                            map.putExtra("lati",String.valueOf(lati));
                                            startActivity(map);

                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });

                        //d.show();
                    }
                        return false;
                    }
                });

        }catch (Exception e){
            e.printStackTrace();
        }

        Intent intent = getIntent();
        if (intent.getIntExtra("Place Number",0) == 0 ){

            // Zoom into users location
            locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    centreMapOnLocation(location,"ME");
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centreMapOnLocation(lastKnownLocation,"ME");
            } else {

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }


    }


}