package com.example.teamdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TeamAddActivity extends AppCompatActivity {

    private EditText et_TeamName;
    private EditText et_member1;
    private EditText et_member2;
    private EditText et_member3;
    private EditText et_member4;

    private String TeamName;
    private String member1;
    private String member2;
    private String member3;
    private String member4;


    private DataBaseManager dataBaseManager;
    private HashMapConvertor hashMapConvertor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_team_add);
        et_TeamName = findViewById(R.id.et_teamname);
        et_member1 = findViewById(R.id.et_member1);
        et_member2 = findViewById(R.id.et_member2);
        et_member3 = findViewById(R.id.et_member3);
        et_member4 = findViewById(R.id.et_member4);

        dataBaseManager = new DataBaseManager();


    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //firebase에 접속하여 팀 생성하기 코드 필요
        TeamName = et_TeamName.getText().toString();
        member1 = et_member1.getText().toString();
        member2 = et_member2.getText().toString();
        member3 = et_member3.getText().toString();
        member4 = et_member4.getText().toString();

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        TeamData temp = new TeamData(TeamName, data, member1, member2, member3, member4);
        temp.setKVc();
        hashMapConvertor = new HashMapConvertor(temp.getKeys(), temp.getValues());
        dataBaseManager.writeNewTeam(hashMapConvertor.getHashMap());
        finish();
    }

}
