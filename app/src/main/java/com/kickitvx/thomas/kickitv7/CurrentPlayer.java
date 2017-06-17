package com.kickitvx.thomas.kickitv7;

import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

/**
 * Created by Thomas on 4/29/2017.
 */

public class CurrentPlayer {
    public static Player currentProfile = new Player();
    public CurrentPlayer()
    {
        if(currentProfile == null)
            currentProfile = new Player();
    }
    public static Player profileInstance()
    {
        if(currentProfile== null)
            currentProfile = new Player();
        return currentProfile;
    }
    public Player getCurrentProfile()
    {
        return currentProfile;

    }

    public void setCurrentPlayer(Player player)
    {
        currentProfile = player;
    }
    public Player getPlayerProfile(final Context context)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        final Vector<Player> thePlayerHolder = new Vector<Player>();
        final Vector<Player> allThePlayers = new Vector<Player>();
        databaseReference.child("players").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> players = dataSnapshot.getChildren();
                for(DataSnapshot child : players) {
                    Player myNewPlayer = child.getValue(Player.class);
                    allThePlayers.add(myNewPlayer);
                }
                for(int index = 0; index < allThePlayers.size(); index++)
                {
                    if(allThePlayers.get(index).getUserKey() == mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid())
                    {
                        thePlayerHolder.add(allThePlayers.get(index));
                    }

                }




            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(thePlayerHolder.size() > 0) {
            Player returnPlayer = thePlayerHolder.get(0);
            return returnPlayer;
        }
        else
        {
            Player returnPlayer = new Player();
            return returnPlayer;
        }

    }
}
