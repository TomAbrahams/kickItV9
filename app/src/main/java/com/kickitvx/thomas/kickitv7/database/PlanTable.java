package com.kickitvx.thomas.kickitv7.database;

/**
 * Created by Thomas on 4/23/2017.
 */

/**
 * Created by Thomas on 3/30/2017.
 */

public class PlanTable  {
    public static final String TABLE_PLANS = "plans";
    public static final String COLUMN_ID = "planId";
    public static final String COLUMN_NAME = "planName";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_CURRENTNUM = "currentNum";
    public static final String COLUMN_LIMITER = "Limiter";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_EVENT0CAT = "Event0Category";
    public static final String COLUMN_EVENT0ADDRESS = "Event0Address";
    public static final String COLUMN_EVENT0EVENTNAME = "Event0EventName";
    public static final String COLUMN_EVENT0UNIQUEID = "Event0UniqueID";
    public static final String COLUMN_EVENT1CAT = "Event1Category";
    public static final String COLUMN_EVENT1ADDRESS = "Event1Address";
    public static final String COLUMN_EVENT1EVENTNAME = "Event1EventName";
    public static final String COLUMN_EVENT1UNIQUEID = "Event1UniqueID";
    public static final String COLUMN_EVENT2CAT = "Event2Category";
    public static final String COLUMN_EVENT2ADDRESS = "Event2Address";
    public static final String COLUMN_EVENT2EVENTNAME = "Event2EventName";
    public static final String COLUMN_EVENT2UNIQUEID = "Event2UniqueID";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_PLANS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_CODE + " TEXT, " +
            COLUMN_CURRENTNUM + " INTEGER, " +
            COLUMN_LIMITER + " INTEGER, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_EVENT0CAT + " TEXT, " +
            COLUMN_EVENT0ADDRESS + " TEXT, " +
            COLUMN_EVENT0EVENTNAME + " TEXT, " +
            COLUMN_EVENT0UNIQUEID + " TEXT, " +
            COLUMN_EVENT1CAT + " TEXT, " +
            COLUMN_EVENT1ADDRESS + " TEXT, " +
            COLUMN_EVENT1EVENTNAME + " TEXT, " +
            COLUMN_EVENT1UNIQUEID + " TEXT, " +
            COLUMN_EVENT2CAT + " TEXT, " +
            COLUMN_EVENT2ADDRESS + " TEXT, " +
            COLUMN_EVENT2EVENTNAME + " TEXT, " +
            COLUMN_EVENT2UNIQUEID + " TEXT" + ");";
    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_PLANS;
    public static final String[] ALL_COLUMNS =
            {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION,COLUMN_CODE,COLUMN_CURRENTNUM,
                    COLUMN_LIMITER,COLUMN_EMAIL, COLUMN_EVENT0CAT,COLUMN_EVENT0ADDRESS,
                    COLUMN_EVENT0EVENTNAME, COLUMN_EVENT0UNIQUEID,
                    COLUMN_EVENT1CAT, COLUMN_EVENT1ADDRESS,COLUMN_EVENT1EVENTNAME,COLUMN_EVENT1UNIQUEID,
                    COLUMN_EVENT2CAT,COLUMN_EVENT2ADDRESS,COLUMN_EVENT2EVENTNAME,COLUMN_EVENT2UNIQUEID
            };

}
