package com.example.jiwon.smartlogger_koreatech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by jiwon on 2018-02-17.
 */



import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GpsActivity extends FragmentActivity implements OnMapReadyCallback {
    private String id, level;
    private GoogleMap mMap;
    ImageView gpsMainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        level = intent.getStringExtra("level");


        gpsMainBtn = (ImageView)findViewById(R.id.gpsMainBtn);


        gpsMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level.equals("commonUser")){
                    Intent mainIntent = new Intent(getApplicationContext(),commonuserMainActivity.class);
                    mainIntent.putExtra("id",id);
                    mainIntent.putExtra("level",level);
                    startActivity(mainIntent);
                }
                else if(level.equals("teamUser") || level.equals("administrator")){
                    Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                    mainIntent.putExtra("id",id);
                    mainIntent.putExtra("level",level);
                    startActivity(mainIntent);
                }
                finish();

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // LatLng(Latitude,Longtitude)넣으면 되는거니까!! .아두이노 gps에서 받아온 값 계속 넣어주면 돼!!
        LatLng location = new LatLng(36.763500, 127.280654);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker"));
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(location, 17);
        mMap.animateCamera(yourLocation);
    }
}
