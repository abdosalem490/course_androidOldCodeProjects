package com.abdosalm.bio.data;

public class Bio
{
    private String name;
    private String hobbies;

    public Bio() {
    }

    public Bio(String name, String hobbies) {
        this.name = name;
        this.hobbies = hobbies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }


}

