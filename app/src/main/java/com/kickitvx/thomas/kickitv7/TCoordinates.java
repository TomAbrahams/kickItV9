package com.kickitvx.thomas.kickitv7;

/**
 * Created by Thomas on 4/23/2017.
 */

public class TCoordinates {
    private double tLongitude = 0.0;
    private double tLatitidue = 0.0;

    public TCoordinates() {
        tLongitude = 0.0;
        tLatitidue = 0.0;

    }
    public TCoordinates(double myLongitude, double myLatitidue)
    {
        tLongitude = myLongitude;
        tLatitidue = myLatitidue;

    }
    public double getLongitude()
    {
        return tLongitude;
    }
    public double getLatitude()
    {
        return tLatitidue;

    }
    public void setLongitude(double myLongitude)
    {
        tLongitude = myLongitude;
    }
    public void setLatitude(double myLatitude)
    {
        tLatitidue = myLatitude;
    }
}
