package com.example.team_p;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DataBaseManager {
    private DatabaseReference mDatabase;

    public DataBaseManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void writeNewTeam(HashMap<String, Object> data){
        mDatabase.child("team").push().setValue(data);
    }

    public void writeTopic(String team, HashMap<String, Object> data){
        data.put("team", team);
        mDatabase.child("topic").push().setValue(data);
    }
}
