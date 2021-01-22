package com.example.teamdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ProjectAddActivity extends AppCompatActivity {

    private EditText et_projectname;
    private EditText et_desc;

    private String projectName;
    private String desc;
    private Button btn_addproject;
    private DataBaseManager  dataBaseManager ;
    private String teamName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_project_add);
        dataBaseManager = new DataBaseManager();
        et_projectname = findViewById(R.id.et_projectname);
        et_desc = findViewById(R.id.et_desc);

        Intent it = getIntent();
        teamName = it.getStringExtra("data");
        btn_addproject = findViewById(R.id.btn_project_add_execute);

        btn_addproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectName = et_projectname.getText().toString();
                desc = et_desc.getText().toString();
                ProjectData temp = new ProjectData(teamName,projectName, desc);
                temp.setKVs();
                HashMapConvertor hashMapConvertor = new HashMapConvertor(temp.getKeys(), temp.getValues());
                dataBaseManager.writeProject(hashMapConvertor.getHashMap());
                finish();
            }
        });
    }


}
