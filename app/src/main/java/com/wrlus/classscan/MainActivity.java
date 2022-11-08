package com.wrlus.classscan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ClassScan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> parcelables = ParcelableScan.getAllParcelableClasses();
        for (String parcelable : parcelables) {
            Log.d(TAG, parcelable);
        }
        Log.d(TAG, "Total Parcelable classes: " + parcelables.size());
    }
}
