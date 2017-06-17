package com.kickitvx.thomas.kickitv7;

/**
 * Created by Thomas on 4/23/2017.
 */

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Thomas on 2/11/2017.
 */



public class mAuthSingleton {
    private static mAuthSingleton instance;
    private FirebaseAuth myAuth;

    public static mAuthSingleton Instance()
    {
        if(instance == null)
            instance = new mAuthSingleton();
        return instance;
    }

    public FirebaseAuth getmAuth()
    {
        return myAuth;
    }
    public void setmAuth(FirebaseAuth myAuth)
    {
        this.myAuth = myAuth;
    }

}
