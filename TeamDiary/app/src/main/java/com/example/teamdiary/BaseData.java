package com.example.teamdiary;

import java.util.ArrayList;

public class BaseData {
    private ArrayList<String> keys;
    private ArrayList<Object> values;

    protected BaseData() {
        this.keys = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    protected void addHash(String key, Object value){
        keys.add(key);
        values.add(value);
    }

    public ArrayList<String> getKeys() {
        return keys;
    }

    public ArrayList<Object> getValues() {
        return values;
    }
}
