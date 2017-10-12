package com.blockx.greg.becomerich;

/**
 * Created by Bryan on 11/10/2017.
 */

public class HealthManager {

    private int maxHealth = 300;
    private int currentHealth;

    public  int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void addHealth(int amount){
        if(currentHealth > maxHealth)
        {
            currentHealth = maxHealth;
        } else
        {
            currentHealth += amount;
        }
    }

    public void subtractHealth(int amount)
    {
        if(currentHealth <= 0)
        {
            playerDied();
        } else
        {
            currentHealth -= amount;
        }
    }

    public void playerDied()
    {

    }

}
