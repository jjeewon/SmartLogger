package com.example.jiwon.smartlogger_koreatech;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Iterator;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity {
    private DatabaseReference databaseReference;
    private EditText loginId;
    private EditText loginPassword;
    private Button loginBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText loginId = (EditText)findViewById(R.id.loginId);
        final EditText loginPassword = (EditText)findViewById(R.id.loginPassword);
        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);



        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Switching to Register screen
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);
            }

        });


        // LoginButton
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(loginId.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"ID를 입력해 주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(loginPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Password를 입력해 주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Query zonesQuery = databaseReference.orderByChild("userId").equalTo(loginId.getText().toString());
                zonesQuery.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) { //password까지 맞으면 로그인 완료
                            if(loginPassword.getText().toString().equals(zoneSnapshot.child("userPassword").getValue(String.class))){
                                Toast.makeText(getApplicationContext(),"로그인 완료",Toast.LENGTH_LONG).show();

                                if(zoneSnapshot.child("userAccountLevel").getValue(String.class).equals("commonUser")){
                                    Intent intent = new Intent(getApplicationContext(), commonuserMainActivity.class);
                                    intent.putExtra("id",loginId.getText().toString());
                                    intent.putExtra("level",zoneSnapshot.child("userAccountLevel").getValue(String.class));
                                    intent.putExtra("name",zoneSnapshot.child("userName").getValue(String.class));
                                    startActivity(intent);

                                }
                                else if(zoneSnapshot.child("userAccountLevel").getValue(String.class).equals("teamUser")
                                        || zoneSnapshot.child("userAccountLevel").getValue(String.class).equals("administrator")){
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("id",loginId.getText().toString());
                                    intent.putExtra("level",zoneSnapshot.child("userAccountLevel").getValue(String.class));
                                    intent.putExtra("name",zoneSnapshot.child("userName").getValue(String.class));
                                    startActivity(intent);
                                }
                                else if(zoneSnapshot.child("userAccountLevel").getValue(String.class).equals("developer")){

                                    Intent intent = new Intent(getApplicationContext(), DeveloperActivity.class);
                                    startActivity(intent);


                                }

                                finish();
                                return;
                            }
                        }
                        Toast.makeText(getApplicationContext(),"Id와 Password가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "onCancelled", databaseError.toException());
                    }
                });
            }
        });
    }
}
