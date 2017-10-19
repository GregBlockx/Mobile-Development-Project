package com.blockx.greg.becomerich;

import java.lang.reflect.Array;

/**
 * Created by Bryan on 10/10/2017.
 */

public class GameItem {

    private String activityName;
    private int activityAmount;
    private boolean haveBought;
    private int healthValue;
    private String[] requirements;

    public GameItem(String activityName, int activityAmount, String[] requirements){
        this.activityName = activityName;
        this.activityAmount = activityAmount;
        this.requirements = requirements;
    }

    public GameItem(String activityName, int activityAmount, boolean haveBought){
        this.activityName = activityName;
        this.activityAmount = activityAmount;
        this.haveBought = haveBought;
    }

    public GameItem(String activityName, int activityAmount, int healthValue){
        this.activityName = activityName;
        this.activityAmount = activityAmount;
        this.healthValue = healthValue;
    }

    public String[] getRequirements() {
        return requirements;
    }

    public int getHealthValue() {
        return healthValue;
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

    public int getActivityAmount() {
        return activityAmount;
    }

}