package com.example.jiwon.smartlogger_koreatech;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import static java.lang.Thread.sleep;

class User {

    public String userId;
    public String userPassword;
    public String userName;
    public String userAccountLevel;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userId, String userPassword, String userName, String userAccountLevel ) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userAccountLevel = userAccountLevel;
    }

}

public class SigninActivity extends Activity /*implements GoogleApiClient.OnConnectionFailedListener*/{
    private DatabaseReference databaseReference;
    protected Button registerBtn;
    protected SignInButton googleAccountBtn;
    private EditText getReg_id;
    private EditText getReg_password;
    private EditText getReg_name;
    private EditText getReg_teamcord;
    private String accountLevel;
    private boolean isGoogleAccountExisted = false;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 10; //start activity result code



    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        getReg_id = (EditText)findViewById(R.id.reg_id);
        getReg_password = (EditText)findViewById(R.id.reg_password);
        getReg_name = (EditText)findViewById(R.id.reg_name);
        getReg_teamcord = (EditText)findViewById(R.id.reg_teamCord) ;


        // If  you touch login button...
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        loginScreen.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){ // From SigninAcitivity to LoginActivity....
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        /*related to Google Account ....  Start
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        googleAccountBtn = (SignInButton)findViewById(R.id.login);
        googleAccountBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){ //이 버튼 누르면 구글 사용자니? 물음
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        */
        /*related to Google Account ....  Finish */

        registerBtn = (Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                // checkcing 1. id 입력여부, 2. password 입력여부, 3. id 중복여부 4. 구글 계정 인증 여부 5. 팀코드 확인
                databaseReference.addValueEventListener(checkRegister);

            }
        });
    }

    private void writeNewUser(String userId, String userPassword,String userName, String userAccountLevel) {
        User user = new User(userId, userPassword, userName, userAccountLevel);
        databaseReference.child(userId).setValue(user);
}

    private ValueEventListener checkRegister = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
            if(getReg_id.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"ID를 입력해 주세요",Toast.LENGTH_SHORT).show();
                return;
            }
            else if(getReg_password.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Password를 입력해 주세요",Toast.LENGTH_SHORT).show();
                return;
            }
            else if(getReg_name.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Name을 입력해 주세요",Toast.LENGTH_SHORT).show();
                return;
            }

            while (child.hasNext()) {//마찬가지로 중복 유무 확인
                if (getReg_id.getText().toString().equals(child.next().getKey())) {
                    dataSnapshot.child("userId").getValue(String.class);
                    Toast.makeText(getApplicationContext(), "이미 존재하는 아이디 입니다.", Toast.LENGTH_LONG).show();
                    databaseReference.removeEventListener(this);
                    return;
                }
            }
            if(getReg_teamcord.getText().toString().equals("jjeeewon")){
                accountLevel = "administrator";
                Toast.makeText(SigninActivity.this,String.valueOf(accountLevel),Toast.LENGTH_SHORT).show();
            }
            else if(getReg_teamcord.getText().toString().equals("jjeewon")){
                accountLevel = "teamUser";
                Toast.makeText(SigninActivity.this,String.valueOf(accountLevel),Toast.LENGTH_SHORT).show();
            }
            else if(getReg_teamcord.getText().toString().equals("amar1004zzang")){
                accountLevel = "developer";
                Toast.makeText(SigninActivity.this,String.valueOf(accountLevel),Toast.LENGTH_SHORT).show();
            }
            else{
                accountLevel = "commonUser";
                Toast.makeText(SigninActivity.this,String.valueOf(accountLevel),Toast.LENGTH_SHORT).show();
            }

            writeNewUser(getReg_id.getText().toString(),getReg_password.getText().toString(),getReg_name.getText().toString(),accountLevel);
            Toast.makeText(SigninActivity.this,"회원 가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);



        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };



/*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }
    */
/*
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) { //파이어베이스로 값 넘겨주기

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential) //이 사람에 대한 정보를 받아서 인증한것을 파이어베이스에 넘겨줌
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                        }else{
                            isGoogleAccountExisted = true;
                            Toast.makeText(SigninActivity.this,"구글 계정 인증이 완료되었습니다.",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    */
}
