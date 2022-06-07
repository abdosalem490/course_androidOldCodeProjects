package com.example.learnjava;

public class Demo {
    public static void main(String[] args) {

       /* VampireKing dracula = new VampireKing("Dracula");
        dracula.showInfo();
        dracula.setLives(0);
        do {
            if (dracula.dodges()) {
                dracula.setLives(dracula.getLives() + 1);
                continue;
            }
            if (dracula.runAway()) {
                System.out.println("Dracula run away");
                break;
            } else {
                dracula.takeDamage(80);
                dracula.showInfo();
            }
        }while (dracula.getLives() > 0);
        System.out.println("====================================================");*/

        Player conan = new Player("Conan");
        conan.pickUpLoot(new loot("Invisibility", lootType.POTION, 4));
        conan.pickUpLoot(new loot("Mithril", lootType.ARMOR, 183));
        conan.pickUpLoot(new loot("Ring of speed", lootType.RING, 25));
        conan.pickUpLoot(new loot("Red Potion", lootType.POTION, 2));
        conan.pickUpLoot(new loot("Cursed Shield", lootType.ARMOR, -8));
        conan.pickUpLoot(new loot("Brass Ring", lootType.RING, 1));
        conan.pickUpLoot(new loot("Chain Mail", lootType.ARMOR, 4));
        conan.pickUpLoot(new loot("Gold Ring", lootType.RING, 12));
        conan.pickUpLoot(new loot("Health Potion", lootType.POTION, 3));
        conan.pickUpLoot(new loot("Silver Ring", lootType.RING, 6));
        conan.showInventory();

        System.out.println(conan.score());

       conan.dropLoot("Cursed Shield");
        System.out.println(conan.score());
    }
}