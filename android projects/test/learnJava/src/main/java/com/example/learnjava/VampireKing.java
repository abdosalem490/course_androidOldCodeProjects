package com.example.learnjava;

import java.util.Random;

public class VampireKing extends Vampire {
    public VampireKing(String name) {
        super(name);
        setHitPoints(140);
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage / 2);
    }

    public boolean runAway() {
        return (getLives() < 2);
    }

    public boolean dodges()
    {
        Random rand = new Random();
        int chance = rand.nextInt(6);
        if (chance > 3)
        {
            System.out.println("Dracula dodges");
            return true;
        }
        return false;
    }
}
