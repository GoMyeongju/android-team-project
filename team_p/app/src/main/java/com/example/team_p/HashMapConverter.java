package com.example.team_p;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapConverter {
    private ArrayList<String> keys;
    private ArrayList<Object> values;
    private int mapSize = 0;
    private boolean pairCheck;

    //키와 밸류의 값의 개수가 동일해야함
    public HashMapConverter(ArrayList<String> keys, ArrayList<Object> values) {
        if(keys.size() != values.size()){
            pairCheck = false;
        }
        this.mapSize = keys.size();
        this.keys = keys;
        this.values = values;
    }

    public HashMap<String, Object> getHashMap(){
        if(pairCheck){
            return null;
        }
        HashMap<String, Object> result = new HashMap<>();
        for(int i = 0; i < mapSize; i++){
            System.out.println(keys.get(i) + values.get(i));
            System.out.println("testing...");
            result.put(keys.get(i), values.get(i));
        }
        return result;
    }
}
