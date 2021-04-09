package com.example.jiwon.smartlogger_koreatech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

/**
 * Created by jiwon on 2018-02-14.
 */

public class SplashActivity extends Activity {
    private DatabaseReference databaseReference;
    private String IPAddress;
    private Intent intent;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        intent = new Intent(getApplicationContext(),LoginActivity.class);
        databaseReference = FirebaseDatabase.getInstance().getReference("IPAddress");
        Query zonesQuery = databaseReference.orderByChild("userName").equalTo("jiwon");
        zonesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                    IPAddress = zoneSnapshot.child("IPAddress").getValue(String.class);
                    Log.d("MQTT",IPAddress);
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });

        try{
            Thread.sleep(2000); //대기 초 설정
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        startActivity(intent);
        finish();
    }
}
