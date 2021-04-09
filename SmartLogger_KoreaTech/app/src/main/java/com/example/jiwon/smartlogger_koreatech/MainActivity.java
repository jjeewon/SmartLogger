package com.example.jiwon.smartlogger_koreatech;

import tp.skt.onem2m.api.IMQTT;
import tp.skt.onem2m.api.MQTTProcessor;
import tp.skt.onem2m.api.oneM2MAPI;
import tp.skt.onem2m.binder.mqtt_v1_1.Binder;
import tp.skt.onem2m.binder.mqtt_v1_1.Definitions;
import tp.skt.onem2m.binder.mqtt_v1_1.Definitions.Operation;
import tp.skt.onem2m.binder.mqtt_v1_1.control.execInstanceControl;
import tp.skt.onem2m.binder.mqtt_v1_1.request.AE;
import tp.skt.onem2m.binder.mqtt_v1_1.request.CSEBase;
import tp.skt.onem2m.binder.mqtt_v1_1.request.areaNwkInfo;
import tp.skt.onem2m.binder.mqtt_v1_1.request.container;
import tp.skt.onem2m.binder.mqtt_v1_1.request.contentInstance;
import tp.skt.onem2m.binder.mqtt_v1_1.request.execInstance;
import tp.skt.onem2m.binder.mqtt_v1_1.request.locationPolicy;
import tp.skt.onem2m.binder.mqtt_v1_1.request.mgmtCmd;
import tp.skt.onem2m.binder.mqtt_v1_1.request.node;
import tp.skt.onem2m.binder.mqtt_v1_1.request.remoteCSE;
import tp.skt.onem2m.binder.mqtt_v1_1.response.AEResponse;
import tp.skt.onem2m.binder.mqtt_v1_1.response.CSEBaseResponse;
import tp.skt.onem2m.binder.mqtt_v1_1.response.ResponseBase;
import tp.skt.onem2m.binder.mqtt_v1_1.response.areaNwkInfoResponse;
import tp.skt.onem2m.binder.mqtt_v1_1.response.containerResponse;
import tp.skt.onem2m.binder.mqtt_v1_1.response.contentInstanceResponse;
import tp.skt.onem2m.binder.mqtt_v1_1.response.execInstanceResponse;
import tp.skt.onem2m.binder.mqtt_v1_1.response.locationPolicyResponse;
import tp.skt.onem2m.binder.mqtt_v1_1.response.mgmtCmdResponse;
import tp.skt.onem2m.binder.mqtt_v1_1.response.nodeResponse;
import tp.skt.onem2m.binder.mqtt_v1_1.response.remoteCSEResponse;
import tp.skt.onem2m.net.mqtt.MQTTCallback;
import tp.skt.onem2m.net.mqtt.MQTTClient;
import tp.skt.onem2m.net.mqtt.MQTTConfiguration;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.vision.text.Text;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.sleep;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    public static MainActivity __instance;

    /** Called when the activity is first created. */

    Intent intent;
    private String id;
    private String level;
    private TextView info;
    private GoogleMap mMap;
    public Handler handler;
    private boolean adminSign = false;

    //main sensor value
    private TextView velocityValue;
    private TextView rpmValue;
    private TextView batteryValue;
    private TextView switchValue;


    //main function Imagebuttons
    private ImageView gpsBtn;
    private ImageView driverBtn;
    private ImageView adminBtn;
    public TextView textViewData;


    //realated to chat fuction
    private FirebaseDatabase chatFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference chatDatabaseReference = chatFirebaseDatabase.getReference();
    private DatabaseReference databaseReference;
    private SharedPreferences pref;
    private EditText editText;
    private Button sendBtn;
    private ArrayAdapter adapter;
    private String userName;
    private String IPAddress = "192.168.1.2";






    public MainActivity() {
        handler = new Handler();
    }
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /// synchronized(this) {
      //      __instance = this;
      //  }
       // Log.d("TEST", ">>>>>>>>>>>>>>>>>>>>>>>>>> " + MainActivity.__instance);




       // intent.putExtra("testModel", testModel);
        startService(intent);
        Intent serviceIntent = new Intent();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        info = (TextView)findViewById(R.id.info);
        //ImageButtons
        textViewData = (TextView)findViewById(R.id.info);


        velocityValue = (TextView)findViewById(R.id.Velocity_value) ;
        rpmValue = (TextView)findViewById(R.id.RPM_value);
        batteryValue = (TextView)findViewById(R.id.Battery_value);

        //MqttClientKetiSub의 messageArrived에서 오는 값 전달 받음
       //
        // velocityValue.setText(pref.getString("velocity",""));
       // rpmValue.setText(pref.getString("rpm",""));
       // batteryValue.setText(pref.getString("battery",""));

        gpsBtn = (ImageView)findViewById(R.id.gpsBtn);
        driverBtn= (ImageView)findViewById(R.id.driverBtn);
        adminBtn = (ImageView)findViewById(R.id.adminBtn);

        //onClickListener setting
        gpsBtn.setOnClickListener(mClickListener);
        driverBtn.setOnClickListener(mClickListener);
        adminBtn.setOnClickListener(mClickListener);

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


        // Tab2 Setting for teamUser & administrator
        ImageView chatBtn = new ImageView(this);
        chatBtn.setImageResource(R.drawable.chatting);
        TabSpec tabSpec2 = tabHost.newTabSpec("Tab2");
        tabSpec2.setIndicator(chatBtn); // Tab Subject
        tabSpec2.setContent(R.id.tab_view1); // Tab Content
        tabHost.addTab(tabSpec2);

        editText = (EditText)findViewById(R.id.editText);
        sendBtn = (Button)findViewById(R.id.sendBtn);
        userName = intent.getStringExtra("name");




        //리스트뷰
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1);
        ListView listview = (ListView)findViewById(R.id.chatListView);
        listview.setAdapter(adapter);

        //전송 버튼
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals("")){
                    ChatData chatData = new ChatData(userName, editText.getText().toString());
                    chatDatabaseReference.child("message").push().setValue(chatData);
                    editText.setText("");
                }
            }
        });

        chatDatabaseReference.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class); //chatData 가져오기
                adapter.add(chatData.getUserName()+ ": " + chatData.getMessage());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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

    public void setSensorValue(String container, String content){
        System.out.println("[&CubeThyme] MQTT container : " + container+", content : "+content);
        if(container.equals("battery")){
            batteryValue.setText(content);
        }else if(container.equals("gyro")){
            rpmValue.setText(content);
        }else if(container.equals("switch")){
            switchValue.setText(content);
        }else if(container.equals("gps")){
            velocityValue.setText(content);
        }
    }










    public void onRestart(View v){
        super.onRestart();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        level = intent.getStringExtra("level");

        info.setText("\n"+"                "+level+"  "+id+" 님 안뇽");
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
                case R.id.driverBtn:
                    if(level.equals("administrator")){
                        driverMessageDialog();
                        break;
                    } else
                        break;

                case R.id.adminBtn:
                    //관리자 계정일 경우에만 adminDialog / cancelAdminDialog 뜨게 처리해야 함
                    if(level.equals("administrator")){
                        if(adminSign == false)
                            adminDialog();
                        else
                            cancelAdminDialog();
                        break;
                    }else
                        break;

                default:
                    break;

            }
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // LatLng(Latitude,Longtitude)넣으면 되는거니까!! .아두이노 gps에서 받아온 값 계속 넣어주면 돼!!
        LatLng location = new LatLng(36.763500, 127.280654);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker"));
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(location, 17);
        mMap.animateCamera(yourLocation);
    }

    private void driverMessageDialog(){
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.driver_dialog,(ViewGroup) findViewById(R.id.layout_root));
        AlertDialog.Builder aDialog = new AlertDialog.Builder(this);
        aDialog.setTitle("운전자에게 메세지를 보내시겠습니까?");
        aDialog.setView(layout);

        aDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //라즈베리파이-아두이노 lcd로 메세지 보내는 코드 작성!!
                EditText driverMessage = (EditText)findViewById(R.id.driverMessage);
                if (!driverMessage.equals("")) {

                }
                else {
                    Toast.makeText(getApplicationContext(), "메세지를 입력하여 주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        aDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog ad = aDialog.create();
        ad.show();
    }



    private void adminDialog(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("관리자 비상 신호를 보내시겠습니까?").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'Yes' Button
                        adminSign = true;
                        //1. 비상 신호를 빨간색으로 바꾸는 함수
                        adminBtn.setImageResource(R.drawable.admistrator_red);

                        //2. 비상 신호를 라즈베리파이(->아두이노 LCD에 출력하는 함수)
                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        // Title for AlertDialog
        alert.setTitle("관리자 비상 신호");
        // Icon for AlertDialog
        alert.setIcon(R.drawable.admistrator_red);
        alert.show();
    }

    private void cancelAdminDialog(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("관리자 비상 신호를 철회하시겠습니까?").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'Yes' Button
                        adminSign = false;
                        //1. 비상 신호를 검정색으로 바꾸는 함수
                        adminBtn.setImageResource(R.drawable.admistrator);

                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        // Title for AlertDialog
        alert.setTitle("관리자 비상신호 철회");
        // Icon for AlertDialog
        alert.setIcon(R.drawable.admistrator_red);
        alert.show();
    }


}





