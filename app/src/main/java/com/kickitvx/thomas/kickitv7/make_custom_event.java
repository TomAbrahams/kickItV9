package com.kickitvx.thomas.kickitv7;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class make_custom_event extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private final String TAG = "CREATE_EVENT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_custom_event);
        //Getting Authorization.
        //mAuth = mAuthSingleton.Instance().getmAuth();
        fireAddPlan();
        goToEvent();
    }
    public void fireAddPlan()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button buttonAddPlan1 = (Button)findViewById(R.id.btnAddEvent1);
        Button buttonAddPlan2 = (Button)findViewById(R.id.btnAddEvent2);
        Button buttonAddPlan3 = (Button)findViewById(R.id.btnAddEvent3);
        Button buttonAddPlan4 = (Button)findViewById(R.id.btnAddEvent4);

        buttonAddPlan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //EditText
                EditText eventName = (EditText)findViewById(R.id.etEventName);
                EditText eventAddress1 = (EditText)findViewById(R.id.etEventAddress);
                EditText eventAddress2 = (EditText)findViewById(R.id.etEventCitySt);
                EditText eventCategory = (EditText)findViewById(R.id.etEventCat);
                //New
                EditText codeTxt = (EditText)findViewById(R.id.etCode);
                String myEventName = eventName.getText().toString();
                String myEventAddress = eventAddress1.getText().toString() + " " +
                        eventAddress2.getText().toString();
                String myCategory = eventCategory.getText().toString();
                String uniqueKey = "plan-" +
                        mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();
                //This is new
                String email = mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail();
                String code = codeTxt.getText().toString();
                if(code.isEmpty())
                    code = "No password";
                //End new
                DefaultPlan defaultPlan = new DefaultPlan();
                defaultPlan.myPlanInstance().Event0.setTheEvent(myEventName,myEventAddress,myCategory,uniqueKey);
                defaultPlan.myPlanInstance().setEmailCode(email, code);
                /*
                    Plan myPlan = new Plan();
                    myPlan.Event0.setTheEvent(myEventName,myEventAddress,myCategory,uniqueKey);
                    myPlan.setEmailCode(email,code); //This is new.
                    mDatabase.child("plans").child(uniqueKey).setValue(myPlan);
                */
                Toast.makeText(getBaseContext(), "Event 1 Added! ",
                        Toast.LENGTH_LONG).show();
            }
        });
        buttonAddPlan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //EditText
                EditText eventName = (EditText)findViewById(R.id.etEventName);
                EditText eventAddress1 = (EditText)findViewById(R.id.etEventAddress);
                EditText eventAddress2 = (EditText)findViewById(R.id.etEventCitySt);
                EditText eventCategory = (EditText)findViewById(R.id.etEventCat);


                EditText codeTxt = (EditText)findViewById(R.id.etCode);     //New

                String myEventName = eventName.getText().toString();
                String myEventAddress = eventAddress1.getText().toString() + " " +
                        eventAddress2.getText().toString();
                String myCategory = eventCategory.getText().toString();
                String uniqueKey = "plan-" +
                        mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();

                //This is new
                String email = mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail();
                String code = codeTxt.getText().toString();
                if(code.isEmpty())
                    code = "No password";
                //End new
                //Default Plan.
                DefaultPlan defaultPlan = new DefaultPlan();
                defaultPlan.myPlanInstance().Event1.setTheEvent(myEventName,myEventAddress,myCategory,uniqueKey);
                defaultPlan.myPlanInstance().setEmailCode(email, code);
                /*
                    Plan myPlan = new Plan();
                    myPlan.Event1.setTheEvent(myEventName,myEventAddress,myCategory,uniqueKey);
                    myPlan.setEmailCode(email,code); //This is new.
                    mDatabase.child("plans").child(uniqueKey).setValue(myPlan);
                */
                Toast.makeText(getBaseContext(), "Event 2 Added! ",
                        Toast.LENGTH_LONG).show();

            }
        });
        buttonAddPlan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //EditText
                EditText eventName = (EditText)findViewById(R.id.etEventName);
                EditText eventAddress1 = (EditText)findViewById(R.id.etEventAddress);
                EditText eventAddress2 = (EditText)findViewById(R.id.etEventCitySt);
                EditText eventCategory = (EditText)findViewById(R.id.etEventCat);

                EditText codeTxt = (EditText)findViewById(R.id.etCode);     //New

                String myEventName = eventName.getText().toString();
                String myEventAddress = eventAddress1.getText().toString() + " " +
                        eventAddress2.getText().toString();
                String myCategory = eventCategory.getText().toString();
                String uniqueKey ="plan-" +
                        mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();


                //This is new
                String email = mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail();
                String code = codeTxt.getText().toString();
                if(code.isEmpty())
                    code = "No password";
                //End new
                DefaultPlan defaultPlan = new DefaultPlan();
                defaultPlan.myPlanInstance().Event2.setTheEvent(myEventName,myEventAddress,myCategory,uniqueKey);
                defaultPlan.myPlanInstance().setEmailCode(email, code);
                /*
                    Plan myPlan = new Plan();
                    myPlan.Event2.setTheEvent(myEventName,myEventAddress,myCategory,uniqueKey);
                    myPlan.setEmailCode(email,code); //This is new.
                    mDatabase.child("plans").child(uniqueKey).setValue(myPlan);
                */
                Toast.makeText(getBaseContext(), "Event 3 Added! ",
                        Toast.LENGTH_LONG).show();

            }
        });
        buttonAddPlan4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intentTry = new Intent(make_custom_event.this, CurrentPlan.class );
                startActivity(intentTry);
            }
        });
    }
    private void goToEvent()
    {
        final Context context = this;
        Button eventBtn = (Button)findViewById(R.id.btnAddEvent4);
        eventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v)
            {
                Intent intentNxt = new Intent(context, showItems.class);
                startActivity(intentNxt);
            }
        });
    }
}
