package com.blockx.greg.becomerich;

/**
 * Created by Bryan on 10/10/2017.
 */

public class Activity {

    String activityName;
    int activityAmount;

    public Activity(String activityName, int activityAmount){
        this.activityName = activityName;
        this.activityAmount = activityAmount;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityAmount() {
        return activityAmount;
    }

    public void setActivityAmount(int activityAmount) {
        this.activityAmount = activityAmount;
    }

}
