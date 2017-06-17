package com.kickitvx.thomas.kickitv7;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kickitvx.thomas.kickitv7.database.DBAssistant;
import com.google.firebase.auth.FirebaseAuth;

public class Main_Menu extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);
        //TODO: Get a reference to the firebase auth object
        mAuth = FirebaseAuth.getInstance();
        clickingButtons();
        getItFromOnline();
        signOut();

    }
    public void getItFromOnline()
    {
        final Context context = Main_Menu.this;
        //CurrentPlayer.currentProfile.getPlayerProfile(context);
        try {
            String userId = mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid().toString();
            CurrentPlayer theCurrentPlayer = new CurrentPlayer();
            Player thePlayer = theCurrentPlayer.getCurrentProfile();
            thePlayer.retreivePlayerProfile(context);
            theCurrentPlayer.setCurrentPlayer(thePlayer);
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(), "Can't find the profile.",
                    Toast.LENGTH_LONG).show();
            CurrentPlayer theCurrentPlayer = new CurrentPlayer();
            Player thePlayer = new Player();
            theCurrentPlayer.setCurrentPlayer(thePlayer);
        }
    }
    private void clickingButtons(){
        MyTextViewFuture createEvent = (MyTextViewFuture) findViewById(R.id.btnCreateEvent);
        MyTextViewFuture joinEvent = (MyTextViewFuture) findViewById(R.id.btnJoinEvent);
        MyTextViewFuture loadEvent = (MyTextViewFuture) findViewById(R.id.btnLoadEvent);
        MyTextViewFuture profile = (MyTextViewFuture) findViewById(R.id.btnProfile);

        createEvent.setOnClickListener( new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                Intent myIntent = new Intent(Main_Menu.this, CurrentPlan.class);
                                                //Bundle bundle = new Bundle();
                                                //bundle.putInt("eventNum",theEventNumber);
                                                //myIntent.putExtras(bundle);
                                                startActivity(myIntent);


                                            }
                                        }
        );
        joinEvent.setOnClickListener( new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v)
                                          {
                                              Intent myIntent = new Intent(Main_Menu.this, searchCode.class);
                                              //Bundle bundle = new Bundle();
                                              //bundle.putInt("eventNum",theEventNumber);
                                              //myIntent.putExtras(bundle);
                                              startActivity(myIntent);


                                          }
                                      }
        );
        loadEvent.setOnClickListener( new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v)
                                          {
                                              Intent myIntent = new Intent(Main_Menu.this, LoadPlans.class);
                                              //Bundle bundle = new Bundle();
                                              //bundle.putInt("eventNum",theEventNumber);
                                              //myIntent.putExtras(bundle);
                                              startActivity(myIntent);


                                          }
                                      }
        );
        profile.setOnClickListener( new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v)
                                          {
                                              Intent myIntent = new Intent(Main_Menu.this, Profile.class);
                                              startActivity(myIntent);


                                          }
                                      }
        );

    }

    private void signOut() {

        MyTextViewFuture signOutBtn = (MyTextViewFuture) findViewById(R.id.btnLogout);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              mAuth.signOut();
                                              DefaultPlan myDefault = new DefaultPlan();
                                              myDefault.clearEvent1();
                                              myDefault.clearEvent2();
                                              myDefault.clearEvent3();
                                              Toast.makeText(Main_Menu.this, "Logged out", Toast.LENGTH_SHORT)
                                                      .show();

                                              Intent myIntent = new Intent(Main_Menu.this, sign_in.class);
                                              //Bundle bundle = new Bundle();
                                              //bundle.putInt("eventNum",theEventNumber);
                                              //myIntent.putExtras(bundle);
                                              startActivity(myIntent);


                                          }
                                      }
        );
    }
    public void goToProfile()
    {
        Intent myIntent = new Intent(Main_Menu.this, Profile.class);
        startActivity(myIntent);

    }
}