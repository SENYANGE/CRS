package com.example.crs;



import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class CRS_Persistent  extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }