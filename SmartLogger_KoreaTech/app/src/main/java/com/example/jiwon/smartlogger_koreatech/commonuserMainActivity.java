package com.example.jiwon.smartlogger_koreatech;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class commonuserMainActivity extends Activity {

    /** Called when the activity is first created. */
    Intent intent;
    private String id;
    private String level;
    private TextView info;

    private GoogleMap mMap;

    private boolean adminSign = false;

    //main function Imagebuttons
    private ImageView gpsBtn;

    //realated to chat fuction
    private FirebaseDatabase chatFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference chatDatabaseReference = chatFirebaseDatabase.getReference();
    private EditText editText;
    private Button sendBtn;
    private ArrayAdapter adapter;
    private String userName;

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commonusermain);

        info = (TextView)findViewById(R.id.info);
        //ImageButtons

        gpsBtn = (ImageView)findViewById(R.id.gpsBtn);
        //onClickListener setting
        gpsBtn.setOnClickListener(mClickListener);


        intent = getIntent();
        id = intent.getStringExtra("id");
        level = intent.getStringExtra("level");
        info.setText("\n"+"                "+level+"  "+id+" 님 안뇽");
        TabHost tabHost = (TabHost)findViewById(R.id.tab_host);
        tabHost.setup();


        // Tab1 Setting
        ImageView mainBtn = new ImageView(this);
        mainBtn.setImageResource(R.drawable.main);
        TabSpec tabSpec1 = tabHost.newTabSpec("Tab1");
        tabSpec1.setIndicator(mainBtn); // Tab Subject
        tabSpec1.setContent(R.id.main); // Tab Content
        tabHost.addTab(tabSpec1);

        // Tab2 Setting for commonUser
        ImageView chatBtn = new ImageView(this);
        chatBtn.setImageResource(R.drawable.chatting);
        TabSpec tabSpec2 = tabHost.newTabSpec("Tab2");
        tabSpec2.setIndicator(chatBtn); // Tab Subject
        tabSpec2.setContent(R.id.tab_view1);
        tabHost.addTab(tabSpec2);


        // Tab3 Setting
        ImageView historyBtn = new ImageView(this);
        historyBtn.setImageResource(R.drawable.history);
        TabSpec tabSpec3 = tabHost.newTabSpec("Tab3");
        tabSpec3.setIndicator(historyBtn); // Tab Subject
        tabSpec3.setContent(R.id.tab_view3); // Tab Content
        tabHost.addTab(tabSpec3);

        // Tab4 Setting
        ImageView logoutBtn = new ImageView(this);
        logoutBtn.setImageResource(R.drawable.logout);
        TabSpec tabSpec4 = tabHost.newTabSpec("Tab4");
        tabSpec4.setIndicator(logoutBtn); // Tab Subject
        tabSpec4.setContent(new Intent(this,LoginActivity.class)); // Tab Content
        tabHost.addTab(tabSpec4);
        //logoutBtn.setOnClickListener(this);
        // show Center Tab Content
        tabHost.setCurrentTab(0);

    }

    ImageView.OnClickListener mClickListener = new View.OnClickListener(){
        public void onClick(View v){
            switch(v.getId()){
                case R.id.gpsBtn:
                    Intent gpsIntent = new Intent(getApplicationContext(),GpsActivity.class);
                    gpsIntent.putExtra("id",id);
                    gpsIntent.putExtra("level",level);
                    startActivity(gpsIntent);
                    break;

                default:
                    break;

            }
        }
    };


}





