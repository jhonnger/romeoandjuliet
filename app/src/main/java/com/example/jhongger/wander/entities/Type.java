package com.example.jhongger.wander.entities;

/**
 * Created by jhongger on 27/02/2017.
 */

public class Type {
    private String name;
    private String description;

    public Type() {

    }
    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
