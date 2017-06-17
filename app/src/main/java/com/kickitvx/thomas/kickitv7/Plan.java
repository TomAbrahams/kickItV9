package com.kickitvx.thomas.kickitv7;

/**
 * Created by Thomas on 4/23/2017.
 */

import android.content.ContentValues;

import com.kickitvx.thomas.kickitv7.database.PlanTable;

/**
 * Created by Thomas on 2/12/2017.
 */

public class Plan {
    private String userIdKey;
    public Event Event0;
    public Event Event1;
    public Event Event2;
    public Event Event3;
    public int limiter = 4;
    public int currentNum = 0;
    private String code ="None";
    private String email = "None";
    private String planName = "Plan Name";
    private String description = "Description";
    public Plan()
    {
        Event0 = new Event();
        Event1 = new Event();
        Event2 = new Event();
        Event3 = new Event();

    }
    public void setEmailCode(String myEmail, String myCode)
    {
        email = myEmail;
        code = myCode;

    }
    public void setUserIdKey(String myUserIdKey)
    {
        userIdKey = myUserIdKey;
    }
    public String getEmail()
    {
        return email;
    }
    public String getCode()
    {
        return code;
    }
    public String getUserIdKey()
    {
        return userIdKey;
    }

    public void setCurrentNum(int number)
    {
        currentNum = number;

    }
    public void setPlanName(String newPlanName)
    {
        planName = newPlanName;
    }
    public String getPlanName()
    {
        return planName;
    }
    public void setDescription(String newDescription)
    {
        description = newDescription;
    }
    public String getDescription()
    {
        return description;
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues(19);

        values.put(PlanTable.COLUMN_NAME, planName);
        values.put(PlanTable.COLUMN_DESCRIPTION, description);
        values.put(PlanTable.COLUMN_CODE, code);
        values.put(PlanTable.COLUMN_CURRENTNUM, currentNum);
        values.put(PlanTable.COLUMN_LIMITER, limiter);
        values.put(PlanTable.COLUMN_EMAIL, email);
        values.put(PlanTable.COLUMN_EVENT0CAT, Event0.getCategory());
        values.put(PlanTable.COLUMN_EVENT0ADDRESS, Event0.getEventAddress());
        values.put(PlanTable.COLUMN_EVENT0EVENTNAME, Event0.getEventName());
        values.put(PlanTable.COLUMN_EVENT0UNIQUEID, Event0.getUniqueId());
        values.put(PlanTable.COLUMN_EVENT1CAT, Event1.getCategory());
        values.put(PlanTable.COLUMN_EVENT1ADDRESS, Event1.getEventAddress());
        values.put(PlanTable.COLUMN_EVENT1EVENTNAME, Event1.getEventName());
        values.put(PlanTable.COLUMN_EVENT1UNIQUEID, Event1.getUniqueId());
        values.put(PlanTable.COLUMN_EVENT2CAT, Event2.getCategory());
        values.put(PlanTable.COLUMN_EVENT2ADDRESS, Event2.getEventAddress());
        values.put(PlanTable.COLUMN_EVENT2EVENTNAME, Event2.getEventName());
        values.put(PlanTable.COLUMN_EVENT2UNIQUEID, Event2.getUniqueId());
        values.put(PlanTable.COLUMN_NAME, planName);
        return values;

    }

    public void pushed(Event theEvent)
    {
        if (currentNum % limiter == 0)
        {
            Event0.setTheEvent(theEvent.getEventName(),theEvent.getEventAddress(),
                    theEvent.getCategory(),
                    theEvent.getUniqueId());

        }
        else if(currentNum % limiter == 1)
        {
            Event1.setTheEvent(theEvent.getEventName(),theEvent.getEventAddress(),
                    theEvent.getCategory(),
                    theEvent.getUniqueId());
        }
        else if(currentNum % limiter == 2)
        {
            Event2.setTheEvent(theEvent.getEventName(),theEvent.getEventAddress(),
                    theEvent.getCategory(),
                    theEvent.getUniqueId());
        }
        else if(currentNum % limiter == 3)
        {
            Event3.setTheEvent(theEvent.getEventName(),theEvent.getEventAddress(),
                    theEvent.getCategory(),
                    theEvent.getUniqueId());
        }

    }


}