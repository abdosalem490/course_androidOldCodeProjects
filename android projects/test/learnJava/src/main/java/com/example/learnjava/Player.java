package com.example.learnjava;

import java.util.ArrayList;

public class Player
{
   private String handleName;
    private int lives;
    private int level;
    private int score;
    private Weapn weapn;
    private ArrayList<loot> inventory;

    public Player()
    {
        this("UnKnown Player");
    }
    public Player(String handle)
    {
        this(handle , 1);
    }
    public Player(String handle , int startingLevel)
    {
        /*this.handleName = handle;
        this.level = startingLevel;
        this.lives = 3;
        this.score = 0;*/
        setHandleName(handle);
        setLives(3);
        setLevel(startingLevel);
        setScore(0);
        setDefaultWeapon();
        inventory = new ArrayList<>();
    }
    public String getHandleName()
    {
        return this.handleName;
    }
    public void setHandleName(String handle)
    {
        if (handle.length() < 4)
        {
            System.out.println("The name "+handle+" is too short");
            return;
        }
        this.handleName = handle;
    }

    private void setDefaultWeapon()
    {
        this.weapn = new Weapn(10 , 20 , "Sword");
    }

    public int getLives() {
        return this.lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void setNameSndLevel(String name , int level)
    {
      /*  this.level = level;
        this.handleName = name;*/
        setHandleName(name);
        setLevel(level);
    }

    public Weapn getWeapn() {
        return weapn;
    }

    public void setWeapn(Weapn weapn) {
        this.weapn = weapn;
    }

    public ArrayList<loot> getInventory() {
        return inventory;
    }

  /*  public void setInventory(ArrayList<loot> inventory) {
        this.inventory = inventory;
    }*/
    public void pickUpLoot(loot newLoot)
    {
        inventory.add(newLoot );
    }
    public boolean dropLoot(loot l)
    {
        if (this.inventory.contains(l))
        {
            inventory.remove(l);
            return true;
        }
        return false;
    }
    public void showInventory()
    {
        for (loot item : inventory)
        {
            System.out.println(item.getName());
        }
        System.out.println("=================================");
    }
    public int score()
    {
        int total = 0 ;
       // for (int i = 0 ; i < inventory.size() ; i++)
        for (loot currentLoot : inventory)
        {
         //  loot currentLoot = inventory.get(i);
            System.out.println(currentLoot.getName()+" is worth "+currentLoot.getValue());
            total = total + currentLoot.getValue();
        }
        return total;
    }
    public boolean dropLoot(String lootName)
    {
        for (loot currentLoot : inventory)
        {
            if (currentLoot.getName().equals(lootName))
            {
                inventory.remove(currentLoot);
                return true;
            }
        }
        return false;
    }
}
