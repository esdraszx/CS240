package com.example.client;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import layout.MyMapFragment;

public class MyMapActivity extends AppCompatActivity {

    private String IdOfEvent = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IdOfEvent = getIntent().getExtras().getString("personID");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);

        FragmentManager fm = getSupportFragmentManager();
        MyMapFragment mapFrag = (MyMapFragment) fm.findFragmentById(R.id.myMapFragmentDifferent);


        if(mapFrag==null) {
            mapFrag=new MyMapFragment();
            mapFrag.setEventId(IdOfEvent);
            mapFrag.setComingFromPerson(true);
            mapFrag.setContext(this);
            fm.beginTransaction()
                    .replace(R.id.mapLayout, mapFrag)
                    .commit();
        }
    }

}
