package com.example.team_p;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Join extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

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

    InputMethodManager imm;

    int number =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        et_join_email = findViewById(R.id.et_join_email);
        et_join_pwd = findViewById(R.id.et_join_pwd);
        et_join_pwd_check = findViewById(R.id.et_join_pwd_check);
        et_join_name = findViewById(R.id.et_join_name);
        et_join_school = findViewById(R.id.et_join_school);

        //키보드 제어
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        firebaseAuth = FirebaseAuth.getInstance();

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
                pwd = et_join_pwd.getText().toString();
                pwd_check = et_join_pwd_check.getText().toString();
                name = et_join_name.getText().toString();
                school = et_join_school.getText().toString();

                join_check(email, pwd, pwd_check, name, school);
                if(number == 5){
                    user_auth(email, pwd);
                }
            }
        });
    }

    public void user_auth(String email, String pwd){
        firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                }else{
                    Toast.makeText(getApplicationContext(),"회원가입에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    //회원가입 전체 유효성 판단
    public void join_check(String email, String pwd, String pwd_check, String name, String school){
        check_email(email);
        check_pwd(pwd, pwd_check);
        check_name(name);
        check_school(school);
    }


    //이메일형식체크
    public void check_email(String email){


        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(getApplicationContext(),"이메일이 형식에 맞지 않습니다.",Toast.LENGTH_SHORT).show();
            et_join_email.setText("");
            et_join_email.requestFocus();
        }
        number++;
        return;

    }

    //비밀번호 유효성
    public void check_pwd(String pwd, String pwd_check){

        //비밀번호 형식 체크
        if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()_-])(?=.*[a-zA-Z]).{8,16}$", pwd))
        {
            Toast.makeText(getApplicationContext(),"비밀번호 형식을 지켜주세요.",Toast.LENGTH_SHORT).show();
            et_join_pwd.setText("");
            et_join_pwd_check.setText("");
            et_join_pwd.requestFocus();

        }
        //비밀번호 확인과 비밀번호가 입력되었는지
        else if(pwd == pwd_check){
            Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();

            et_join_pwd.setText("");
            et_join_pwd_check.setText("");
            et_join_pwd.requestFocus();
        }

        number++;
        return;

    }

    //이름 입력 체크
    public void check_name(String name){
        if(name == ""){
            Toast.makeText(getApplicationContext(),"이름을 입력하세요.",Toast.LENGTH_SHORT).show();

            et_join_name.setText("");
            et_join_name.requestFocus();
        }
        number++;
        return;

    }

    //학교명 입력 체크
    public void check_school(String school){
        if(school == ""){
            Toast.makeText(getApplicationContext(),"학교를 입력하세요.",Toast.LENGTH_SHORT).show();

            et_join_school.setText("");
            et_join_school.requestFocus();
        }
        number++;
        return;
    }

    //키보드 제어
    public void keyboard_down(View view){
        imm.hideSoftInputFromWindow(et_join_email.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et_join_pwd.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et_join_pwd_check.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et_join_name.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et_join_school.getWindowToken(), 0);
    }

}
