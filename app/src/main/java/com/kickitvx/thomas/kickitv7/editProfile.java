package com.kickitvx.thomas.kickitv7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class editProfile extends AppCompatActivity {

    final CurrentPlayer myProfile = new CurrentPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        loadBoxes();
        changeProfile();
    }
    public void loadBoxes()
    {
        TextView firstNameView = (TextView)findViewById(R.id.etFirstName);
        TextView lastNameView = (TextView)findViewById(R.id.etLastName);
        TextView profileView = (TextView)findViewById(R.id.etProfile);
        TextView imageUrlView = (TextView) findViewById(R.id.etImageURL);
        Player thePlayer = myProfile.profileInstance();
        firstNameView.setText(thePlayer.getFirstName());
        lastNameView.setText(thePlayer.getLastName());
        profileView.setText(thePlayer.getDescription());
        imageUrlView.setText(thePlayer.getPictureUrl());
    }
    public void backToMain(View v)
    {
        Intent myIntent = new Intent(editProfile.this, Main_Menu.class);
        startActivity(myIntent);
    }
    public void changeProfile()
    {
        Button changeProfile = (Button)findViewById(R.id.btnChgProfile);
        final TextView firstNameView = (TextView)findViewById(R.id.etFirstName);
        final TextView lastNameView = (TextView)findViewById(R.id.etLastName);
        final TextView profileView = (TextView)findViewById(R.id.etProfile);
        final TextView imageUrlView = (TextView)findViewById(R.id.etImageURL);
        final Player thePlayer = myProfile.profileInstance();
        changeProfile.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 try {
                                                     thePlayer.setFirstName(firstNameView.getText().toString());
                                                     thePlayer.setLastName(lastNameView.getText().toString());
                                                     thePlayer.setDescription(profileView.getText().toString());
                                                     thePlayer.setPictureUrl(imageUrlView.getText().toString());
                                                     thePlayer.setUserKey(mAuthSingleton.Instance()
                                                     .getmAuth().getCurrentUser().getUid());
                                                     thePlayer.setEmail(mAuthSingleton.Instance()
                                                             .getmAuth().getCurrentUser().getEmail());

                                                     thePlayer.uploadPlayerProfile(thePlayer, editProfile.this);

                                                 }
                                                 catch(Exception e)
                                                 {
                                                     Toast.makeText(editProfile.this, "Please fill out all items", Toast.LENGTH_SHORT)
                                                             .show();
                                                 }
                                             }
                                         }

        );

    }
}
