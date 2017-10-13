package com.blockx.greg.becomerich;

/**
 * Created by Bryan on 10/10/2017.
 */

public class Activity {

    String activityName;
    int activityAmount;
    boolean haveBought;

    public Activity(String activityName, int activityAmount){
        this.activityName = activityName;
        this.activityAmount = activityAmount;
    }

    public Activity(String activityName, int activityAmount, boolean haveBought){
        this.activityName = activityName;
        this.activityAmount = activityAmount;
        this.haveBought = haveBought;
    }

    public boolean isHaveBought() {
        return haveBought;
    }

    public void setHaveBought(boolean haveBought) {
        this.haveBought = haveBought;
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
