package com.example.testproject.models;

public class UtilityPayments {
    public int image;
    public String name;
    public boolean section;


    public UtilityPayments() {

    }

    public UtilityPayments(String name, boolean section) {
        this.name = name;
        this.section = section;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public boolean isSection() {
        return section;
    }
}
