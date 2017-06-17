package com.kickitvx.thomas.kickitv7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class showItems extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private String event1Address;
    private String name;
    private roomSizeSingleton roomService;

    private final Vector<String> theAddresses = new Vector<String>();
    private final String TAG = "CREATE_EVENT";
    private String gameId = "None";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = mAuthSingleton.Instance().getmAuth();
        roomService = new roomSizeSingleton();
        //This will hold our plans.
        //getRoomMates();

        setContentView(R.layout.activity_show_items);
        //readData();
        mapButtonFunction();
        goingBack();
        btnChat();
        loadingPlans();
        goToLoadPlayers();

    }
    public void goToLoadPlayers()
    {
        //roomSizeSingleton.cleaner();
        getRoomMates();
        Button party = (Button)findViewById(R.id.btnParty);
        party.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(showItems.this, LoadPlayers.class);
                startActivity(intent);
            }
        });


    }
    public void btnChat()
    {
        Button chat = (Button)findViewById(R.id.btnChat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatButton();
            }
        });
    }
    public void chatButton()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your chat name:");

        final EditText chatNameField = new EditText(this);

        builder.setView(chatNameField);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = chatNameField.getText().toString();
                if(name != null)
                {
                    Intent intent = new Intent(showItems.this, chat.class);
                    intent.putExtra("userName", name);
                    startActivity(intent);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        builder.show();
    }


    public void mapButtonFunction()
    {

        Button map1Button = (Button)findViewById(R.id.btnMapEvent1);
        map1Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                TextView eventMapBtn1 = (TextView)findViewById(R.id.tvEventNum1);
                String theAddress = event1Address;
                if(!event1Address.isEmpty())
                    sendToActionIntent(theAddress);


            }
        });

        Button map2Button = (Button)findViewById(R.id.btnMapEvent2);
        map2Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                TextView eventMapBtn1 = (TextView)findViewById(R.id.tvEvent2);
                String theAddress = theAddresses.get(1);
                if(!theAddress.isEmpty())
                    sendToActionIntent(theAddress);


            }
        });
        Button map3Button = (Button)findViewById(R.id.btnMapEvent3);
        map3Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                TextView eventMapBtn1 = (TextView)findViewById(R.id.tvEvent2);
                String theAddress = theAddresses.get(2);
                if(!theAddress.isEmpty())
                    sendToActionIntent(theAddress);


            }
        });
    }
    public void getRoomMates()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String playerProfile = "players-" + mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();
        CurrentPlayer currentPlayer = new CurrentPlayer();
        Player playerOne = currentPlayer.getCurrentProfile();
        final String theGameRoom = playerOne.getFavoriteGame();
        roomSizeSingleton.cleaner();    //Added to reset items


        databaseReference.child("players").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Vector<Player> allThePlayers = new Vector<Player>();
                Iterable<DataSnapshot> players = dataSnapshot.getChildren();
                //GET ITEMS from online
                for(DataSnapshot child : players) {
                    Player myNewPlayer = child.getValue(Player.class);
                    allThePlayers.add(myNewPlayer);
                }
                int index = 0;
                boolean found = false;
                if(allThePlayers.size() > 0) {
                    for (index = 0; (index < allThePlayers.size()); index++) {
                        String playerOnlineKey = allThePlayers.get(index).getFavoriteGame();
                        Player myPlayer = allThePlayers.get(index);
                        if (playerOnlineKey.equals(theGameRoom)) {
                            found = true;
                            roomService.Instance().addPlayerToRoom(myPlayer);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void sendToActionIntent(String address)
    {
        StringBuilder uri = new StringBuilder("geo:0,0?q=");
        uri.append(address);
        uri.append("?z=10");

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()));
        startActivity(intent);


    }
    public void goingBack()
    {
        Button back = (Button)findViewById(R.id.btnBackToMain);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomSizeSingleton.cleaner();
                CurrentPlayer currentPlayer = new CurrentPlayer();
                currentPlayer.getCurrentProfile().setCurrentRoom("No room");
                Player myPlayer = currentPlayer.getCurrentProfile();
                                /*
                                myPlayer.setUserKey(userKey);
                                myPlayer.setDescription("Temp");
                                myPlayer.setEmail(theEmail);
                                //myPlayer.setFavoriteFood("x-" +email);
                                myPlayer.setPictureUrl("http://personacentral.com/wp-content/uploads/2016/06/Persona-5-Battle-1.jpg");
                                myPlayer.setFavoriteGame("Breath of the Wild");
                                myPlayer.setCurrentRoom("x-" +email);
                                */
                myPlayer.setFavoriteGame("x-noRoom");

                fireAddPlayer(myPlayer);

                Intent myIntent = new Intent(showItems.this, Main_Menu.class);
                startActivity(myIntent);
            }
        });
    }
    public void fireAddPlayer(final Player myPlayer)
    {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();




                //EditText



                String uniqueKey = "player-" +
                        mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();

                //This is new
                String email = mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail();
                //End new
                mDatabaseRef.child("players").child(uniqueKey).setValue(myPlayer);
                //Toast.makeText(getBaseContext(), "the player has been added",
                  //      Toast.LENGTH_LONG).show();
    }
    public void loadingPlans()
    {
        try {
            DefaultPlan defaultPlan = new DefaultPlan();
            Plan thePlan = defaultPlan.getPlanDefault();
            defaultPlan.setPlanDefault(thePlan);    //Sets the plan
            TextView event1 = (TextView) findViewById(R.id.tvEventNum1);
            TextView event2 = (TextView) findViewById(R.id.tvEventNum2);
            TextView event3 = (TextView) findViewById(R.id.tvEventNum3);
            event1Address = thePlan.Event0.getEventAddress();
            theAddresses.add(thePlan.Event0.getEventAddress());
            event1.setText(thePlan.Event0.getEventName() + "\n" +
                    thePlan.Event0.getEventAddress() + "\n" +
                    thePlan.Event0.getCategory());

            theAddresses.add(thePlan.Event1.getEventAddress());
            event2.setText(thePlan.Event1.getEventName() + "\n" +
                    thePlan.Event1.getEventAddress() + "\n" +
                    thePlan.Event1.getCategory());
            theAddresses.add(thePlan.Event2.getEventAddress());
            event3.setText(thePlan.Event2.getEventName() + "\n" +
                    thePlan.Event2.getEventAddress() + "\n" +
                    thePlan.Event2.getCategory());
        }
        catch (Exception e)
        {

        }
    }

}
