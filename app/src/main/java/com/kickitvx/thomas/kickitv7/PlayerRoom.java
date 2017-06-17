package com.kickitvx.thomas.kickitv7;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import static com.kickitvx.thomas.kickitv7.mAuthSingleton.Instance;

/**
 * Created by Thomas on 4/30/2017.
 */

public class PlayerRoom {
    private String roomName;
    private String emailName;
    private Vector<Player> Players;


    public PlayerRoom(){
        roomName = "None";
        emailName = "None";
        Players = new Vector<Player>();



    }
    public PlayerRoom(String roomName, String emailName, Vector<Player> Players)
    {
        this.roomName = roomName;
        this.emailName = emailName;
        this.Players = Players;
    }
    public String getRoomName()
    {
        return roomName;
    }
    public Boolean isEmpty()
    {
        if (roomName == "None")
            return false;
        else
            return true;
    }
    public String getEmailName()
    {
        return emailName;
    }
    public Vector<Player> getPlayers()
    {
        return Players;
    }
    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }
    public void setEmailName(String emailName)
    {
        this.emailName = emailName;
    }
    public void setPlayers(Vector<Player> Players)
    {
        this.Players = Players;
    }
    public PlayerRoom onlineGetRoom(final String emailName)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final Vector<PlayerRoom> playerRooms = new Vector<PlayerRoom>();
        String key = mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid().toString();
        final String roomName = "playroom-" + key;
        databaseReference.child("playrooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> players = dataSnapshot.getChildren();
                for(DataSnapshot child : players) {

                    PlayerRoom myRoom = child.getValue(PlayerRoom.class);
                    if (myRoom.getEmailName() == emailName) {
                        playerRooms.add(myRoom);
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(playerRooms.size() > 0)
            return playerRooms.get(0);
        else {
            PlayerRoom theRoom = new PlayerRoom();
            return theRoom;
        }
    }
    public void onlineMakeRoom(Context context)
    {
        DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
        String key = mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid().toString();
        String email = mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail().toString();
        CurrentPlayer currentPlayer = new CurrentPlayer();
        String roomName = "playroom-" +key;
        Player mePlayer = CurrentPlayer.profileInstance();
        //Set items.

        Players.add(mePlayer);
        this.emailName = email;
        this.roomName = roomName;
        PlayerRoom myPlayerRoom = new PlayerRoom(roomName, email,Players);
        myDatabaseRef.child("playerProfiles").child(roomName).setValue(myPlayerRoom);
        Toast.makeText(context, "the profile has been uploaded",
                Toast.LENGTH_LONG).show();


    }
}
