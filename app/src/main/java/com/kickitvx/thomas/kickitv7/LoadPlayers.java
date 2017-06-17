package com.kickitvx.thomas.kickitv7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kickitvx.thomas.kickitv7.PlayerAdapter.RecyclerViewPlayerAdapter;

import java.util.List;
import java.util.Vector;

public class LoadPlayers extends AppCompatActivity {


    Vector<Player> listPlayersDB = new Vector<Player>();
    Vector<ProfileEntry> listProfilesDB = new Vector <ProfileEntry>();
    List<String> planNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_players);
        goingBack();


        try {

            PlayerRoom playerRoom = new PlayerRoom();
            String myEmail = mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail().toString();
            //Get items from online.
            Player player = new Player();

            //listPlayersDB = player.retreivePlayersInRoom(LoadPlayers.this);
            listPlayersDB = roomSizeSingleton.Instance().getInstance().roomPlayers;
            Toast.makeText(this, "Got players!", Toast.LENGTH_SHORT).show();
            //It's time to add the items.
            //listPlayersDB = playerRoom.getPlayers();
            //String myData = listPlayersDB.get(0).getEmail();
            for (int i = 0; i < listPlayersDB.size(); i++)
            {
                ProfileEntry myProfileEntry = new ProfileEntry(listPlayersDB.get(i));
                listProfilesDB.add(myProfileEntry);

            }
            RecyclerViewPlayerAdapter adapter = new RecyclerViewPlayerAdapter(listProfilesDB, this);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvPlayers);
            recyclerView.setAdapter(adapter);



        }
        catch (Exception e) {
            Toast.makeText(this, "Database not acquired!", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onPause() {
        //Handles database leaks.
        super.onPause();

    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    public void goingBack()
    {
        Button back = (Button)findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoadPlayers.this, showItems.class);
                startActivity(myIntent);
            }
        });
    }
}
