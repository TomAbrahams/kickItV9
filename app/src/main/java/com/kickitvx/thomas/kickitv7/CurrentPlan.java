package com.kickitvx.thomas.kickitv7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kickitvx.thomas.kickitv7.database.DataSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CurrentPlan extends AppCompatActivity{

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_plan);
        clickingEvent1();
        clickingEvent2();
        clickingEvent3();
        loadPlan();
        clickJoinAPlan();
        fireAddPlan();
        clickSavePlan();

    }
    public void loadPlan()
    {
        TextView tvTheEvent1 = (TextView)findViewById(R.id.tvEventOne);
        TextView tvTheEvent2 = (TextView)findViewById(R.id.tvEventTwo);
        TextView tvTheEvent3 = (TextView)findViewById(R.id.tvEventThree);

        DefaultPlan thePlan = new DefaultPlan();

        tvTheEvent1.setText(thePlan.getEvent1());
        tvTheEvent2.setText(thePlan.getEvent2());
        tvTheEvent3.setText(thePlan.getEvent3());
    }
    public void clickingEvent1()
    {
        final Button btnClickEvent1 = (Button)findViewById(R.id.btnChgEvent1);
        btnClickEvent1.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  //Build the box.

                                                  String eventText = btnClickEvent1.getText().toString();
                                                  if ((eventText == "") || (eventText == null))
                                                      eventText = "Nothing here";
                                                  else
                                                  {
                                                      Dialog theDBox = myDialogOne("Event 1",eventText);
                                                      theDBox.show();


                                                  }

                                              }
                                          }

        );

    }
    public void clickingEvent2()
    {
        final Button btnClickEvent = (Button)findViewById(R.id.btnChgEvent2);
        btnClickEvent.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 //Build the box.

                                                 String eventText = btnClickEvent.getText().toString();
                                                 if ((eventText == "") || (eventText == null))
                                                     eventText = "Nothing here";
                                                 else
                                                 {
                                                     Dialog theDBox = myDialogOne("Event 2",eventText);
                                                     theDBox.show();


                                                 }

                                             }
                                         }

        );

    }
    public void clickingEvent3()
    {
        final Button btnClickEvent = (Button)findViewById(R.id.btnChgEvent3);
        btnClickEvent.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 //Build the box.

                                                 String eventText = btnClickEvent.getText().toString();
                                                 if ((eventText == "") || (eventText == null))
                                                     eventText = "Nothing here";
                                                 else
                                                 {
                                                     Dialog theDBox = myDialogOne("Event 3",eventText);
                                                     theDBox.show();


                                                 }

                                             }
                                         }

        );

    }
    public Dialog myDialogOne(String eventName, String eventDesc) {
        // Use the Builder class for convenient dialog construction
        int eventNumber = 0;
        if(eventName == "Event 1")
            eventNumber = 1;
        else if(eventName == "Event 2")
            eventNumber = 2;
        else if(eventName == "Event 3")
            eventNumber = 3;
        final int theEventNumber = eventNumber;
        final Context context = this;
        String eventText = eventDesc;
        if ((eventText == "") || (eventText == null))
            eventText = "Nothing here";


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(eventName);
        builder.setMessage(eventText)
                .setPositiveButton("Change Event", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Time to make an event. Get me to the dialog box.
                        Intent myIntent = new Intent(CurrentPlan.this, choosingEvent.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("eventNum",theEventNumber);
                        myIntent.putExtras(bundle);
                        startActivity(myIntent);


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    public void clickJoinAPlan(){
        Button joinAPlanBtn = (Button)findViewById(R.id.btnJoinPlan);
        joinAPlanBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //Build the box.
                                                Intent myIntent = new Intent(CurrentPlan.this, Main_Menu.class);
                                                startActivity(myIntent);




                                            }
                                        }

        );

    }
    public void clickSavePlan() {
        Button btnSavePlan = (Button)findViewById(R.id.btnSavePlan);
        btnSavePlan.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               //Build the box.
                                               final EditText txtPlanName = new EditText(CurrentPlan.this);
                                               new AlertDialog.Builder(CurrentPlan.this)
                                                       .setTitle("What do you want to name this plan?")
                                                       .setMessage("Type in the name, don't leave it blank.")
                                                       .setView(txtPlanName)
                                                       .setPositiveButton("Save Plan", new DialogInterface.OnClickListener() {
                                                           public void onClick(DialogInterface dialog, int buttonNum)
                                                           {
                                                               try {
                                                                   DefaultPlan.myPlanInstance().setPlanName(txtPlanName.getText().toString());
                                                                   String what = txtPlanName.getText().toString();
                                                                   what = DefaultPlan.myPlanInstance().getPlanName();
                                                                   DefaultPlan myDefaultPlan = new DefaultPlan();
                                                                   Plan plan = myDefaultPlan.myPlanInstance();

                                                                   plan.setUserIdKey(mAuthSingleton.Instance().getmAuth().getCurrentUser().toString());
                                                                   DataSource myDataSource = new DataSource(CurrentPlan.this);
                                                                   myDataSource.open();
                                                                   String output = myDataSource.savePlan(plan);
                                                                   myDataSource.close();
                                                                   Toast.makeText(getBaseContext(), output,
                                                                           Toast.LENGTH_LONG).show();


                                                               }
                                                               catch (Exception e)
                                                               {
                                                                   String exception = e.toString();
                                                                   String except = e.toString();

                                                               }
                                                           }
                                                       })
                                                       .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                                           public void onClick(DialogInterface dialog, int buttonNum) {

                                                           }
                                                       }).show();

                                               //Save the item




                                           }
                                       }

        );
    }

    public void fireAddPlan()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button buttonAddPlan1 = (Button)findViewById(R.id.btnReady);


        buttonAddPlan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //EditText

                //New
                EditText codeTxt = (EditText)findViewById(R.id.etPlanCode);


                String uniqueKey = "plan-" +
                        mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid();
                //This is new
                String email = mAuthSingleton.Instance().getmAuth().getCurrentUser().getEmail();
                String code = codeTxt.getText().toString();
                if(code.isEmpty())
                    code = "No password";
                //End new
                DefaultPlan theDefaultPlan = new DefaultPlan();
                Plan myPlan = theDefaultPlan.getPlanDefault();
                myPlan.setUserIdKey(mAuthSingleton.Instance().getmAuth().getCurrentUser().getUid());
                myPlan.setEmailCode(email,code);

                mDatabase.child("plans").child(uniqueKey).setValue(myPlan);
                Toast.makeText(getBaseContext(), "the Event has been added",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

}
