package com.kickitvx.thomas.kickitv7.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
//Abrahams, Thomas
//Kick it app.
//With help from many tutorials (Lynda, Stackoverflow), it was a fun experience. Android is alright.
import com.kickitvx.thomas.kickitv7.Plan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 4/23/2017.
 */

public class DataSource { private Context myContext;
    private SQLiteDatabase myDatabase;
    SQLiteOpenHelper myDbHelper;

    public DataSource(Context theContext)
    {
        this.myContext = theContext;
        myDbHelper = new DBAssistant(myContext);
        myDatabase = myDbHelper.getWritableDatabase();

    }
    public void open() {
        myDatabase = myDbHelper.getWritableDatabase();
    }
    public void close() {
        myDbHelper.close();
    }
    public Plan createPlan(Plan plan) {
        ContentValues values = plan.toValues();
        myDatabase.insert(PlanTable.TABLE_PLANS, null, values);

        return plan;
    }
    public void deletePlan(String name)
    {
        myDatabase.delete(PlanTable.TABLE_PLANS, PlanTable.COLUMN_NAME + "=?", new String[] {name});

    }
    public String savePlan(Plan plan)
    {
        String result;
        try {
            ContentValues values = plan.toValues();
            myDatabase.insert(PlanTable.TABLE_PLANS, null, values);
            result = "Plan " + plan.getPlanName() + " Saved";
        }
        catch (Exception e)
        {
            result = e.toString();

        }
        return result;

    }
    public List<Plan> getAllPlans() {
        List<Plan> dataPlans = new ArrayList<>();
        Cursor cursor =  myDatabase.query(PlanTable.TABLE_PLANS, PlanTable.ALL_COLUMNS,
                null, null, null, null, PlanTable.COLUMN_NAME);
        while (cursor.moveToNext())
        {
            Plan plan = new Plan();
            //code
            //email
            plan.setPlanName(cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_NAME)));
            plan.setEmailCode(cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_CODE)));
            //currentNum
            plan.setCurrentNum(0);

            //limiter
            //plan.Event0.setTheEvent(theEventName,theEventAddress,theCategory, uniqueId);
            plan.Event0.setTheEvent(cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EVENT0EVENTNAME)),
                    cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EVENT0ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EVENT0CAT)),
                    cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EVENT0UNIQUEID)));

            //Event1category
            //Event1Address
            //Event1EventName
            //Event1uniqueId
            plan.Event1.setTheEvent(cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EVENT1EVENTNAME)),
                    cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EVENT1ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EVENT1CAT)),
                    cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EVENT1UNIQUEID)));
            //Event2category
            //Event2Address
            //Event2EventName
            //Event2uniqueId
            plan.Event2.setTheEvent(cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EVENT2EVENTNAME)),
                    cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EVENT2ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EVENT2CAT)),
                    cursor.getString(cursor.getColumnIndex(PlanTable.COLUMN_EVENT2UNIQUEID)));

            dataPlans.add(plan);
        }

        return dataPlans;
    }
}