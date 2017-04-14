package com.example.jhongger.wander;

/**
 * Created by jhongger on 27/02/2017.
 */

public class BasicNameValue {
    private String name;
    private String value;

    public BasicNameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
