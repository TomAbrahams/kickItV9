package com.kickitvx.thomas.kickitv7;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Daddy on 4/26/2017.
 */

public class Player {


    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String userKey = "";
    private String pictureUrl = "";
    private String description = "";
    private String favoriteFood = "";
    private String favoriteGame = "";
    private String currentRoom = "";


    public Player()
    {
        firstName = "no first name";
        lastName = "no last name";
        email = "No username";
        userKey = "No key";
        pictureUrl = "https://i.ytimg.com/vi/lcE4-6QGgpc/maxresdefault.jpg";
        description = "No Description";
        favoriteFood = "No favorite food";
        favoriteGame = "No favorite game";
        currentRoom = "No room";

    }
    public Player(String email)
    {
        this.email = email;
        userKey = "No key";
        pictureUrl = "None";
        description = "No Description";
        favoriteFood = "No favorite food";
        favoriteGame = "No favorite game";
        currentRoom = "No Room";
    }
    public Player(String firstName, String lastName, String email, String userId, String userKey,
                  String pictureUrl, String description, String favoriteFood,
                  String favoriteGame, String currentRoom)
    {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userKey = userKey;
        this.pictureUrl = pictureUrl;
        this.description = description;
        this.favoriteFood = favoriteFood;
        this.favoriteGame = favoriteGame;
        this.currentRoom = currentRoom;
    }
    //Setter
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public void setCurrentRoom(String currentRoom) {this.currentRoom = currentRoom;}
    public void setEmail(String email)
    {
        this.email = email;

    }

    public void setUserKey(String userKey)
    {
        this.userKey = userKey;

    }
    public void setPictureUrl(String pictureUrl)
    {
        this.pictureUrl = pictureUrl;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setFavoriteFood(String favoriteFood)
    {
        this.favoriteFood = favoriteFood;

    }
    public void setFavoriteGame(String favoriteGame)
    {
        this.favoriteGame = favoriteGame;
    }
    //Getter
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail()
    {
        return email;
    }
    public String getUserKey()
    {
        return userKey;
    }
    public String getPictureUrl()
    {
        return pictureUrl;
    }
    public String getDescription()
    {
        return description;
    }
    public String getFavoriteFood()
    {
        return favoriteFood;
    }
    public String getFavoriteGame()
    {
        return favoriteGame;
    }



    public String printProfile()
    {
        String currentProfile = "Name: " + firstName + " " + lastName + "\n" +
                "Email: " + email + "\n" +
                "Description: " + description + "\n";
        return currentProfile;

    }
    public void uploadPlayerProfile(Player myPlayer, Context context)
    {
        DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
        //EditText
        String uniqueKey = "player-" +
                mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();
        //This is new
        String email = mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail();
        //End new
        myPlayer.setCurrentRoom(email);
        myDatabaseRef.child("players").child(uniqueKey).setValue(myPlayer);

        Toast.makeText(context, "the profile has been uploaded",
                Toast.LENGTH_LONG).show();
    }
    public void getPlayerProfile(final Context context)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();



        String playerProfile = "players-" + mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();
        //root = FirebaseDatabase.getInstance().getReference().child(playerProfile);
        databaseReference.child("players").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Vector<Player> allThePlayers = new Vector<Player>();
                Iterable<DataSnapshot> players = dataSnapshot.getChildren();
                for(DataSnapshot child : players) {
                    Player myNewPlayer = child.getValue(Player.class);
                    allThePlayers.add(myNewPlayer);
                }
                int index = 0;
                int foundIndex = 0;
                boolean found = false;
                String uniqueKey = mAuthSingleton.Instance().getmAuth()
                        .getCurrentUser().getUid();
                for(index = 0; (index < allThePlayers.size())&&!found; index++)
                {
                    String playPro = allThePlayers.get(index).getUserKey();
                    if(playPro == uniqueKey)
                    {
                        found = true;
                        foundIndex = index;
                    }
                }
                if(found)
                {
                    CurrentPlayer currentProfile = new CurrentPlayer();
                    Player thePlayer = CurrentPlayer.profileInstance();
                    thePlayer = allThePlayers.get(foundIndex);
                    currentProfile.setCurrentPlayer(thePlayer);


                }
                else
                {
                    Toast.makeText(context, "The event was not found",
                            Toast.LENGTH_LONG).show();
                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }
    public void retreivePlayerProfile(final Context context) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String playerProfile = "players-" + mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();

        databaseReference.child("players").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Vector<Player> allThePlayers = new Vector<Player>();
                Iterable<DataSnapshot> players = dataSnapshot.getChildren();
                for(DataSnapshot child : players) {
                    Player myNewPlayer = child.getValue(Player.class);
                    allThePlayers.add(myNewPlayer);
                }
                int index = 0;
                int foundIndex = 0;
                boolean found = false;
                String uniqueKey = mAuthSingleton.Instance().getmAuth()
                        .getCurrentUser().getUid();
                String email = mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail();

                for(index = 0; (index < allThePlayers.size())&&!found; index++)
                {
                    String playerOnlineKey = allThePlayers.get(index).getUserKey();
                    if(playerOnlineKey.equals(uniqueKey))
                    {
                        found = true;
                        foundIndex = index;
                    }
                }
                if(found)
                {
                    CurrentPlayer currentProfile = new CurrentPlayer();
                    Player thePlayer = CurrentPlayer.profileInstance();
                    thePlayer = allThePlayers.get(foundIndex);
                    currentProfile.setCurrentPlayer(thePlayer);


                }
                else
                {
                    Toast.makeText(context, "The event was not found",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public Vector<Player> retreivePlayersInRoom(final Context context) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String playerProfile = "players-" + mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();
        CurrentPlayer currentPlayer = new CurrentPlayer();
        Player playerOne = currentPlayer.getCurrentProfile();
        final String theGameRoom = playerOne.getFavoriteGame();
        final Vector<Player> PlayersInRoom = new Vector<Player>();

        databaseReference.child("players").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Vector<Player> allThePlayers = new Vector<Player>();
                Iterable<DataSnapshot> players = dataSnapshot.getChildren();
                for(DataSnapshot child : players) {
                    Player myNewPlayer = child.getValue(Player.class);
                    allThePlayers.add(myNewPlayer);
                }
                int index = 0;
                int foundIndex = 0;
                boolean found = false;
                String uniqueKey = mAuthSingleton.Instance().getmAuth()
                        .getCurrentUser().getUid();
                String email = mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail();

                for(index = 0; (index < allThePlayers.size())&&!found; index++)
                {
                    String playerOnlineKey = allThePlayers.get(index).getFavoriteGame();
                    if(playerOnlineKey.equals(theGameRoom))
                    {
                        found = true;
                        foundIndex = index;
                        PlayersInRoom.add(allThePlayers.get(index));
                    }
                }
                if(!found)
                {
                    Toast.makeText(context, "The event was not found",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return PlayersInRoom;
    }
    public Vector<Player> retreivePlayersInRoom() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String playerProfile = "players-" + mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();
        CurrentPlayer currentPlayer = new CurrentPlayer();
        Player playerOne = currentPlayer.getCurrentProfile();
        final String theGameRoom = playerOne.getFavoriteGame();
        final Vector<Player> PlayersInRoom = new Vector<Player>();

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
                String uniqueKey = mAuthSingleton.Instance().getmAuth()
                        .getCurrentUser().getUid();
                String email = mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail();

                for(index = 0; (index < allThePlayers.size())&&!found; index++)
                {
                    String playerOnlineKey = allThePlayers.get(index).getFavoriteGame();
                    if(playerOnlineKey.equals(theGameRoom))
                    {
                        found = true;
                        PlayersInRoom.add(allThePlayers.get(index));
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return PlayersInRoom;
    }

}
