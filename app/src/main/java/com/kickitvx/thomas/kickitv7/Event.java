package com.kickitvx.thomas.kickitv7;

/**
 * Created by Thomas on 4/23/2017.
 */
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Thomas on 2/15/2017.
 */

public class Event {
    private String eventName;
    private String eventAddress;
    private String uniqueId;
    private String category;
    private String imageURL;
    private double rating;
    public Event()
    {
        eventName = "None";
        eventAddress = "None";
        uniqueId = "None";
        category = "None";
        imageURL = "None";
        rating =0.0;

    }
    public Event(String theEventName, String theEventAddress, String theCategory, String theUniqueId)
    {
        eventName = theEventName;
        eventAddress = theEventAddress;
        uniqueId = theUniqueId;
        category = theCategory;
        rating = 0.0;
    }
    public Event(String theEventName, String theEventAddress, String theCategory, String theUniqueId, String theImageURL)
    {
        eventName = theEventName;
        eventAddress = theEventAddress;
        uniqueId = theUniqueId;
        category = theCategory;
        imageURL = theImageURL;
        rating = 0.0;
    }
    public Event(String theEventName, String theEventAddress, String theCategory, String theUniqueId, String theImageURL, double theRating)
    {
        eventName = theEventName;
        eventAddress = theEventAddress;
        uniqueId = theUniqueId;
        category = theCategory;
        imageURL = theImageURL;
        rating = theRating;
    }
    public String getEventName()
    {
        return eventName;
    }
    public String getEventAddress()
    {
        return eventAddress;
    }
    public String getUniqueId()
    {
        return uniqueId;
    }
    public String getCategory() {return category;}
    public String getImageURL() {return imageURL;}
    public double getRating() {return rating;}
    public void setEventName(String myEventName) {
        eventName = myEventName;

    }
    public void setImageURL(String theImageURL)
    {
        imageURL = theImageURL;
    }
    public void setEventAddress(String myEventAddress)
    {
        eventAddress = myEventAddress;
    }
    public void setUniqueId(String myUniqueId)
    {
        uniqueId= myUniqueId;
    }
    public void setStars(double theRating) {rating = theRating;}
    public void setStars(String theRating) {rating = Double.parseDouble(theRating);}
    public void setTheEvent(String theEventName, String theEventAddress, String theCategory,
                            String theUniqueId)
    {

        eventName = theEventName;
        eventAddress = theEventAddress;
        category = theCategory;
        uniqueId = theUniqueId;
    }
    public void setTheEvent(String theEventName, String theEventAddress, String theCategory,
                            String theUniqueId, String theImageURL, double theRating)
    {

        eventName = theEventName;
        eventAddress = theEventAddress;
        category = theCategory;
        uniqueId = theUniqueId;
        imageURL = theImageURL;
        rating = theRating;
    }
    public String printTheEvent()
    {
        String myEvent = eventName + " \r\n";
        myEvent += eventAddress + " \r\n";
        //myEvent += category + "\r\n";
        return myEvent;
    }
    public void clearEvent()
    {
        eventName = "None";
        eventAddress = "None";
        uniqueId = "None";
        category = "None";
        imageURL = "None";
        rating =0.0;
    }
}
