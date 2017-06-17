package com.kickitvx.thomas.kickitv7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class searchCode extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_code);
        goingBack();
        readData();
    }
    public void goingBack()
    {
        Button back = (Button)findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(searchCode.this, Main_Menu.class);
                startActivity(myIntent);
            }
        });
    }
    public void readData()
    {
        Button findCodeBtn = (Button)findViewById(R.id.btnSearchForCode);
        //EditText searchTxt = (EditText)findViewById(R.id.etSearchText);

        findCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FirebaseDatabase theDataBase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = theDataBase.getReference();
                //mDatabaseRef  = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("plans").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final List<Plan> allThePlans = new ArrayList<Plan>();
                        //TODO Update the contents of fragments on data retrieval
                        //Plan myPlan = dataSnapshot.getValue(Plan.class);
                        //Get all of the children at this level

                        Iterable<DataSnapshot> planChildren = dataSnapshot.getChildren();
                        for (DataSnapshot child : planChildren) {
                            Plan myNewPlan = child.getValue(Plan.class);
                            allThePlans.add(myNewPlan);

                        }
                        if (allThePlans.size() > 0) {
                            EditText searchTxt = (EditText)findViewById(R.id.etSearchText);
                            EditText emailTxt = (EditText)findViewById(R.id.etEmailTxt);
                            String code = searchTxt.getText().toString();
                            String email = emailTxt.getText().toString();
                            int index = 0;
                            int foundIndex = 0;
                            boolean found = false;
                            for(index = 0; (index < allThePlans.size())&&!found; index++)
                            {
                                if((code.equals(allThePlans.get(index).getCode())) &&
                                        (email.equals(allThePlans.get(index).getEmail())))

                                {
                                    found = true;
                                    foundIndex = index;
                                }
                            }
                            if(found) {
                                Plan thePlan = allThePlans.get(foundIndex);
                                DefaultPlan defaultPlan  = new DefaultPlan();
                                defaultPlan.setPlanDefault(thePlan);    //Sets the plan

                                String userKey = mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();
                                String theEmail = mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail();
                                CurrentPlayer currentPlayer = new CurrentPlayer();
                                currentPlayer.getCurrentProfile().setCurrentRoom(email);
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
                                myPlayer.setFavoriteGame("x-"+email);

                                fireAddPlayer(myPlayer);
                                Intent intent = new Intent(searchCode.this, showItems.class);
                                startActivity(intent);

                            }
                            else
                            {
                                Toast.makeText(searchCode.this, "No fun here!", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                        else
                        {
                            Toast.makeText(searchCode.this, "Nothing matches that query", Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(searchCode.this, "Account creation failed", Toast.LENGTH_SHORT)
                                .show();

                    }
                });

            }
        });
    }
    public void fireAddPlayer(final Player myPlayer)
    {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        String uniqueKey = "player-" +
                mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();

        //This is new

        //End new
        myPlayer.setCurrentRoom(mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail().toString());
        mDatabaseRef.child("players").child(uniqueKey).setValue(myPlayer);

        Toast.makeText(getBaseContext(), "the Event has been added",
                Toast.LENGTH_LONG).show();
    }

}
