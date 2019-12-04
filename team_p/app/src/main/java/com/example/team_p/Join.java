package com.example.team_p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Join extends AppCompatActivity {

    Button btn_cancle;
    Button btn_ok;

    EditText et_join_email;
    EditText et_join_pwd;
    EditText et_join_pwd_check;
    EditText et_join_name;
    EditText et_join_school;

    String email;
    String pwd;
    String pwd_check;
    String name;
    String school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        et_join_email = findViewById(R.id.et_join_email);
        et_join_pwd = findViewById(R.id.et_join_pwd);
        et_join_pwd_check = findViewById(R.id.et_join_pwd_check);
        et_join_name = findViewById(R.id.et_join_name);
        et_join_school = findViewById(R.id.et_join_school);


        //취소 버튼 눌렀을 때 다시 로그인 화면으로 돌아가도록 함.
        btn_cancle = findViewById(R.id.btn_cancel);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //가입 버튼 눌렀을 때
        btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int check_num = 0;

                email = et_join_email.getText().toString();

                //이메일형식체크
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(getApplicationContext(),"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
                    return;

                }

            }
        });
    }

}
