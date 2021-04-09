package com.example.jiwon.smartlogger_koreatech;

/**
 * Created by jiwon on 2018-02-12.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class ChatData{
    private String userName;
    private String message;

    public ChatData(){}

    public ChatData(String userName, String message){
        this.userName = userName;
        this.message = message;
    }
    public String getUserName(){ return userName; }
    public String getMessage(){ return message; }
    public void setUserName(String userName){ this.userName = userName; }
    public void setMessage(String message){ this.message = message; }

}

public class ChatActivity extends AppCompatActivity{
    private FirebaseDatabase chatFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference chatDatabaseReference = chatFirebaseDatabase.getReference();

    private EditText editText;
    private Button sendBtn;
    private ArrayAdapter adapter;
    private String userName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        editText = (EditText)findViewById(R.id.editText);
        sendBtn = (Button)findViewById(R.id.sendBtn);
        userName = "김지원";

        //리스트뷰
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1);
        ListView listview = (ListView)findViewById(R.id.chatListView);
        listview.setAdapter(adapter);

        //전송 버튼
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatData chatData = new ChatData(userName, editText.getText().toString());
                chatDatabaseReference.child("message").push().setValue(chatData);
                editText.setText("");
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


    }
}
