package com.example.team_p;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.HashMap;

public class startActivity extends AppCompatActivity {
    private DataBaseManager test;
    private TeamData testing;
    private HashMapConverter t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing);

        String[] member = {"세운", "태용", "인환", "광호"};
        testing = new TeamData("14모임", "태용", member);
        test = new DataBaseManager();
        testing.setKVc();
        t1 = new HashMapConverter(testing.getKeys(), testing.getValues());
        HashMap<String, Object> te  = t1.getHashMap();
        System.out.println(t1.getHashMap().get("TemaName"));
        Date t = new Date();
        TopicData test10 = new TopicData("topic1", "for testing...", "2019/12/05");
        test10.setKVs();
        t1 = new HashMapConverter(test10.getKeys(), test10.getValues());
        test.writeTopic("우리틤~",t1.getHashMap());
        test.writeNewTeam(te);
    }
}
