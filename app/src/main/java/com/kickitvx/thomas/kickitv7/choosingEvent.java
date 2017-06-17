package com.kickitvx.thomas.kickitv7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
//Abrahams, Thomas
//Kick it app.
//With help from many tutorials (Lynda, Stackoverflow), it was a fun experience. Android is alright.
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class choosingEvent extends AppCompatActivity {

    public long LOCATION_REFRESH_TIME =   0;
    public long LOCATION_REFRESH_DISTANCE = 0;
    public int requestMe = 314;
    public String theCLL;
    public double longitude;
    public double latitude;
    public TCoordinates thePlace;
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };
    private Location lastLocation;


    private final LocationListener myLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            /*
            if(location != null))
            {
                //thePlace.setLatitude(location.getLatitude());
                //thePlace.setLongitude(location.getLongitude());
            }
            */
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_choosing_event);
        //Find location.
        if(!canAccessLocation())
            requestPermissions(INITIAL_PERMS,requestMe);
        Bundle bundle = getIntent().getExtras();

        int theEventNumber = bundle.getInt("eventNum");
        setTextBoxToEventNum(theEventNumber);
        findLocation(theEventNumber);
        //For custom location
        customLocation();
        goingBack();
        giveLocationData(theEventNumber);


    }
    public void goingBack()
    {
        Button myCustomBtn= (Button)findViewById(R.id.btnBack);
        myCustomBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v)
                    {
                        Intent myIntent = new Intent(choosingEvent.this, CurrentPlan.class);
                        startActivity(myIntent);
                    }
                }
        );

    }

    public void customLocation()
    {
        Button myCustomBtn= (Button)findViewById(R.id.btnCustom);
        myCustomBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v)
                    {
                        Intent myIntent = new Intent(choosingEvent.this, make_custom_event.class);
                        startActivity(myIntent);
                    }
                }
        );
    }
    public void giveLocationData(int myEventNumber)
    {
        final int eventNumber = myEventNumber;
        Button btnGiveLocation= (Button)findViewById(R.id.btnGiveLocation);
        btnGiveLocation.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v)
                    {

                        Intent myIntent = new Intent(choosingEvent.this, findByZipCode.class);
                        myIntent.putExtra("theCLL", theCLL);
                        myIntent.putExtra("eventNumber", eventNumber);
                        startActivity(myIntent);
                    }
                }
        );
    }
    public void setTextBoxToEventNum(int eventNum)
    {
        TextView theView = (TextView)findViewById(R.id.tvCurrentLocation);
        String theText = "The Event number you have chosen is Event " + eventNum;
        theView.setText(theText);
    }
    public void findLocation(int myEventNumber)
    {
        final int eventNumber = myEventNumber;
        Button myGPSBtn = (Button)findViewById(R.id.btnGetLocation);
        myGPSBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v)
                    {
                        LocationManager myLocationManager;

                        TextView myTextView = (TextView)findViewById(R.id.tvCurrentLocation);

                        myLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                        try {
                            myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                                    LOCATION_REFRESH_DISTANCE, myLocationListener);
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                                    LOCATION_REFRESH_DISTANCE, myLocationListener);
                            Location location = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null)
                            {

                                longitude = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                        .getLongitude();
                                latitude = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                        .getLatitude();

                                theCLL = latitude + "," + longitude;    //Got my latitude and longitude.
                                myTextView.setText("Current Location is Longitude " + longitude + " and " +
                                        "latitude " + latitude);
                                myTextView.setText("Location Found!");
                                Intent myIntent = new Intent(choosingEvent.this, suggestOnGPS.class);
                                myIntent.putExtra("theCLL", theCLL);
                                myIntent.putExtra("eventNumber", eventNumber);

                                startActivity(myIntent);
                            }
                            else
                            {
                                myTextView.setText("No GPS found");
                            }

                        }
                        catch (SecurityException e)
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(choosingEvent.this).create();
                            alertDialog.setTitle("Problem with permissions");
                            alertDialog.setMessage(e.getMessage());
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }




                    }
                }
        );


    }
    private boolean checkPermission(String perm)
    {
        //Check to see if permission is granted.
        return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));

    }
    private boolean canAccessLocation()
    {
        return (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

}
