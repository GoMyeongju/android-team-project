package com.example.teamdiary;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DataBaseManager {
    private DatabaseReference mDatabase;

    public DataBaseManager(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void writeNewUser(HashMap<String, Object> data){
        mDatabase.child("user").push().setValue(data);
    }

    public void writeNewTeam(HashMap<String, Object> data){
        mDatabase.child("team").push().setValue(data);
    }

    public void writeTopic(String team, HashMap<String, Object> data){
        data.put("team", team);
        mDatabase.child("topic").push().setValue(data);
    }
    public void writeProject(HashMap<String, Object> data){
        mDatabase.child("project").push().setValue(data);
    }

    public void writeNewSch(HashMap<String, Object> data){
        mDatabase.child("res").child("sch").push().setValue(data);
    }
    public DatabaseReference getmDatabase(){
        return mDatabase;
    }

}
