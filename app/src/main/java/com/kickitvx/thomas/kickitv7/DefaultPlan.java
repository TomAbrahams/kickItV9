package com.kickitvx.thomas.kickitv7;

/**
 * Created by Thomas on 4/23/2017.
 */

public class DefaultPlan {
    public static Plan planDefault;     //This will have the default plan.

    public DefaultPlan()
    {
        if(planDefault == null)
            planDefault = new Plan();
    }
    public static Plan myPlanInstance()
    {
        if(planDefault == null)
            planDefault = new Plan();
        return planDefault;
    }



    public Plan getPlanDefault()
    {
        return planDefault;
    }
    public void setPlanDefault(Plan newPlanDefault)
    {
        planDefault= newPlanDefault;
    }
    public void setEvent1(Event theEvent)
    {
        planDefault.Event0 = theEvent;
    }
    public void setEvent2(Event theEvent)
    {
        planDefault.Event1 = theEvent;
    }
    public void setEvent3(Event theEvent)
    {
        planDefault.Event2 = theEvent;
    }
    public void setCode(String theEmail, String theCode)
    {
        planDefault.setEmailCode(theEmail, theCode);
    }
    public String getEvent1()
    {
        return planDefault.Event0.printTheEvent();
    }
    public String getEvent2()
    {
        return planDefault.Event1.printTheEvent();
    }
    public String getEvent3()
    {
        return planDefault.Event2.printTheEvent();
    }
    public void clearEvent1()
    {
        planDefault.Event0.clearEvent();
    }
    public void clearEvent2()
    {
        planDefault.Event1.clearEvent();
    }
    public void clearEvent3()
    {
        planDefault.Event2.clearEvent();
    }
}