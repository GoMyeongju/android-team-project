package com.example.teamdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainContentsActivity extends AppCompatActivity {

    private String projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincontents);
        Intent intent = getIntent();
        projectName = intent.getStringExtra("ProjectName");

    }

    public void onClick(View v){
        Intent it;
        switch (v.getId()){
            case R.id.btn_topic:
                it = new Intent(MainContentsActivity.this, TopicListActivity.class);
                it.putExtra("ProjectName", projectName);
                startActivity(it);
                break;
            case R.id.btn_calendar:
                it = new Intent(MainContentsActivity.this, CalendarActivity.class);
                it.putExtra("ProjectName", projectName);
                startActivity(it);
                break;
            case R.id.btn_info:
                it = new Intent(MainContentsActivity.this, MemberInfoActivity.class);
                it.putExtra("ProjectName", projectName);
                startActivity(it);
                break;
        }
    }
}
