package com.kickitvx.thomas.kickitv7;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class sign_in extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "FB_SIGNIN";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText etPass;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        findViewById(R.id.btnCreate).setOnClickListener(this);
        findViewById(R.id.btnSignIn).setOnClickListener(this);
        findViewById(R.id.btnSignOut).setOnClickListener(this);

        etEmail =(EditText)findViewById(R.id.txtEmail);
        etPass = (EditText)findViewById(R.id.txtPassword);

        //TODO: Get a reference to the firebase auth object
        mAuth = FirebaseAuth.getInstance();

        //Todo: Attach a new Auth Listner to detect sign in and out.
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Log.d(TAG, "Signed in: "+ user.getUid());

                }
                else {
                    Log.d(TAG, "Currently signed out");
                }
            }
        };

        clickBack();
    }
    //Implementing on Click Listener.
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnSignIn:
                signUserIn();
                break;
            case R.id.btnCreate:
                createUserAccount();
                break;
            case R.id.btnSignOut:
                signUserOut();
                break;
        }
    }
    /**
     * When the Activity starts and stops, the app needs to connect and
     * disconnect the AuthListener
     */
    @Override
    public void onStart() {
        super.onStart();
        //Connects authlistner when starts.
        // TODO: add the AuthListener
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        // TODO: Remove the AuthListener
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void clickBack(){
        final Context context = this;
        Button backButton = (Button)findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                Intent myIntent = new Intent(context, Main_Menu.class);
                startActivity(myIntent);


            }
        });
    }

    private boolean checkFormFields() {
        String email, password;

        email = etEmail.getText().toString();
        password = etPass.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Email Required");
            return false;
        }
        if (password.isEmpty()){
            etPass.setError("Password Required");
            return false;
        }

        return true;
    }

    private void updateStatus() {
        TextView tvStat = (TextView)findViewById(R.id.tvSignInStatus);
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            tvStat.setText("Signed in: " + user.getEmail() + " uid:" + user.getUid());

        }
        else {
            tvStat.setText("Signed Out");
        }
    }

    private void updateStatus(String stat) {
        //setContentView(R.layout.activity_sign_in);
        TextView tvStat = (TextView)findViewById(R.id.tvSignInStatus);
        tvStat.setText(stat);

    }

    private void signUserIn() {
        if (!checkFormFields())
            return;
        final Context context = this;
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();


        final String theFinalEmail = email;
        final String thePassword = password;
        // TODO: sign the user in with email and password credentials
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String userID = mAuth.getCurrentUser().getUid().toString();
                                    Toast.makeText(sign_in.this, "Signed in "+ userID, Toast.LENGTH_LONG)
                                            .show();
                                    //Signing In
                                    //intent.putExtra("CURRENTUSERAUTH", mAuth.getCurrentUser());
                                    //Intent intent3 = new Intent(context, create_event.class);
                                    Intent intent3 = new Intent(context, Main_Menu.class);

                                    intent3.putExtra("currentUser", userID);
                                    intent3.putExtra("email", theFinalEmail);
                                    intent3.putExtra("password", thePassword);
                                    mAuthSingleton.Instance().setmAuth(mAuth);
                                    startActivity(intent3);
                                }
                                else {
                                    Toast.makeText(sign_in.this, "Sign in failed", Toast.LENGTH_SHORT)
                                            .show();
                                }

                                updateStatus();
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            updateStatus("Invalid password.");
                        }
                        else if (e instanceof FirebaseAuthInvalidUserException) {
                            updateStatus("No account with this email.");
                        }
                        else {
                            updateStatus(e.getLocalizedMessage());
                        }
                    }
                });
    }

    private void signUserOut() {
        // TODO: sign the user out
        mAuth.signOut();
        DefaultPlan myDefault = new DefaultPlan();
        myDefault.clearEvent1();
        myDefault.clearEvent2();
        myDefault.clearEvent3();
        updateStatus();
        CurrentPlayer currentPlayer = new CurrentPlayer();
        Player player = new Player();
        currentPlayer.setCurrentPlayer(new Player());
        //Start the activity
        final Context context = this;
        Intent myIntent = new Intent(context, splashscreen.class);
        startActivity(myIntent);
    }

    private void createUserAccount() {
        if (!checkFormFields())
            return;

        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        // TODO: Create the user account
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(sign_in.this, "User created", Toast.LENGTH_SHORT)
                                            .show();
                                } else {
                                    Toast.makeText(sign_in.this, "Account creation failed", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.toString());
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            updateStatus("This email address is already in use.");
                        }
                        else {
                            updateStatus(e.getLocalizedMessage());
                        }
                    }
                });
    }
    public void getPermissions()
    {

    }
}