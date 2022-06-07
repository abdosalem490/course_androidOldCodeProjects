package com.example.learnjava;

public class Weapn
{
    private String name;
    private int damageInflicted;
    private int hitPoints;

    public Weapn(int damageInflicted, int hitPoints ,String name ) {
        this.name = name;
        this.damageInflicted = damageInflicted;
        this.hitPoints = hitPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamageInflicted() {
        return damageInflicted;
    }

    public void setDamageInflicted(int damageInflicted) {
        this.damageInflicted = damageInflicted;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }


}
