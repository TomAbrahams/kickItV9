package com.kickitvx.thomas.kickitv7;

/**
 * Created by Thomas on 4/23/2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kickitvx.thomas.kickitv7.DefaultPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class chat extends AppCompatActivity {

    private String currentPlanId = DefaultPlan.myPlanInstance().getUserIdKey();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private ArrayList<String> list_of_messages = new ArrayList<>();
    private Button btnSend;
    private EditText input_msg;
    private TextView chat_conversation;
    private String userName;
    private String temp_key;
    private String chat_msg, chat_user_name;
    /*
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private String name;

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        btnSend = (Button)findViewById(R.id.btnSend);
        input_msg = (EditText)findViewById(R.id.etMsgInput);
        chat_conversation = (TextView)findViewById(R.id.tvMessages);
        userName = getIntent().getExtras().get("userName").toString();
        if(currentPlanId != null)
        {
            makeRoom();  //Make the room.
            getMessages(); //get the messages.
        }

    }
    public void makeRoom()
    {
        String chatRoom = "chat-" + currentPlanId;  //Make the chat room.
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(chatRoom,"");
        root.updateChildren(map);

    }
    public void getMessages()
    {
        String room_name = "chat-" + currentPlanId;
        root = FirebaseDatabase.getInstance().getReference().child(room_name);
        btnSend.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Map<String, Object> map = new HashMap<String, Object>();
                                           temp_key = root.push().getKey();
                                           root.updateChildren(map);

                                           DatabaseReference message_root = root.child(temp_key);
                                           Map<String,Object> map2 = new HashMap<String,Object>();
                                           map2.put("name",userName);
                                           map2.put("msg",input_msg.getText().toString());
                                           message_root.updateChildren(map2);
                                       }
                                   }
        );

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addToChatMessages(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                addToChatMessages(dataSnapshot);
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
    private void addToChatMessages(DataSnapshot dataSnapshot)
    {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            try {

                chat_msg = (String) ((DataSnapshot) i.next()).getValue();
                chat_user_name = (String) ((DataSnapshot) i.next()).getValue();
                chat_conversation.append(chat_user_name + " : " + chat_msg + " \n");
            }
            catch (Exception e)
            {
                String exception = e.toString();
                String hello = "hello!";

            }

        }

    }
}