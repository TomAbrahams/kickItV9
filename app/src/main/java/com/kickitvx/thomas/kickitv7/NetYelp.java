package com.kickitvx.thomas.kickitv7;

import android.os.AsyncTask;

/**
 * Created by Thomas on 4/23/2017.
 */


public class NetYelp extends AsyncTask<String, String, String> {
    public AsyncResponse delegate = null;
    @Override
    protected String doInBackground(String... params) {

        String myTerm = params[0];  //"Ramen";
        String theCLL = params[1];  //"34.052235, -118.243683";
        String type = params[2];    //Indicator
        String theBody = "";
        while(theBody == null || theBody =="")
        {
            //YelpAPICLI yelpApiCli = new YelpAPICLI();
            //new JCommander(yelpApiCli, );
            Event myNewEvent = new Event();
            YelpAPI yelpAPI = new YelpAPI();
            if(type.equals("GPS"))
                theBody = yelpAPI.queryAPIZ(yelpAPI, myTerm, theCLL, myNewEvent);
            else
                theBody = yelpAPI.queryAPIX(yelpAPI, myTerm, theCLL);

        }
        return theBody;    //The event. Call method set get.
/*
        catch (Exception e) {
            String theError = e.getLocalizedMessage();
            return "Failed";
        }*/
    }

    @Override
    protected void onPostExecute(String body) {
        delegate.processFinish(body);
    }
}
