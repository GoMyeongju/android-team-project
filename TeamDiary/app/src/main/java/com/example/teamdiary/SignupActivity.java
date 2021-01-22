package com.example.teamdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    //파이어 베이스 인증 관리자
    private FirebaseAuth firebaseAuth;
    //파이어 베이스 데이터베이스 관리자
    private DatabaseReference databaseReference;

    //회원가입 데이터
    private EditText et_signup_email;
    private EditText et_signup_pwd;
    private EditText et_signup_pwd_check;
    private EditText et_signup_name;
    private EditText et_signup_school;
    private EditText et_signup_phone;

    private String email;
    private String password;
    private String password_check;
    private String name;
    private String school;
    private String phone;
    private InputMethodManager imm;
    private int number = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //파이어 베이스 인증 관리자 등록
        firebaseAuth = FirebaseAuth.getInstance();

        //파이어 베이스 데이터베이스 관리자 등록
        databaseReference = FirebaseDatabase.getInstance().getReference();

        imm = (InputMethodManager) this.getSystemService(SignupActivity.INPUT_METHOD_SERVICE);


        //위젯 연결
        et_signup_email = findViewById(R.id.et_signup_email);
        et_signup_pwd = findViewById(R.id.et_signup_password);
        et_signup_pwd_check = findViewById(R.id.et_signup_password_check);
        et_signup_name = findViewById(R.id.et_signup_name);
        et_signup_school = findViewById(R.id.et_signup_school);
        et_signup_phone = findViewById(R.id.et_signup_phone);
    }

    public void onClick(View v){

        switch (v.getId()){
            //회원가입 실행
            case R.id.btn_signup_execute:
                int check_num = 0;

                email = et_signup_email.getText().toString();
                password = et_signup_pwd.getText().toString();
                password_check = et_signup_pwd_check.getText().toString();
                name = et_signup_name.getText().toString();
                school = et_signup_school.getText().toString();
                phone = et_signup_phone.getText().toString();
                join_check(email, password, password_check, name, school, phone);
                if(number == 6) {
                    signUp(name, email, school, phone);
                }
                break;
            //회원가입 취소
            case R.id.btn_cancel:
                //로그인 창으로 복귀
                finish();
                break;
            case R.id.lo_signup:
                //키보드 내리기 (편의성)
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }
    }

    //회원가입 전체 유효성 판단
    public void join_check(String email, String pwd, String pwd_check, String name, String school, String phone){
        check_email(email);
        check_pwd(pwd, pwd_check);
        check_name(name);
        check_school(school);
        check_phone(phone);
    }


    //이메일형식체크
    public void check_email(String email){
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(getApplicationContext(),"이메일이 형식에 맞지 않습니다.",Toast.LENGTH_SHORT).show();
            et_signup_email.setText("");
            et_signup_email.requestFocus();
        }
        number++;
    }

    //비밀번호 유효성
    public void check_pwd(String pwd, String pwd_check){

        //비밀번호 형식 체크
        if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()_-])(?=.*[a-zA-Z]).{8,16}$", pwd))
        {
            Toast.makeText(getApplicationContext(),"비밀번호 형식을 지켜주세요.",Toast.LENGTH_SHORT).show();
            et_signup_pwd.setText("");
            et_signup_pwd_check.setText("");
            et_signup_pwd.requestFocus();

        }
        //비밀번호 확인과 비밀번호가 입력되었는지
        else if(pwd == pwd_check){
            Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();

            et_signup_pwd.setText("");
            et_signup_pwd_check.setText("");
            et_signup_pwd.requestFocus();
        }

        number++;
    }

    //이름 입력 체크
    public void check_name(String name){
        if(name == ""){
            Toast.makeText(getApplicationContext(),"이름을 입력하세요.",Toast.LENGTH_SHORT).show();

            et_signup_name.setText("");
            et_signup_name.requestFocus();
        }
        number++;
    }

    //학교명 입력 체크
    public void check_school(String school){
        if(school == ""){
            Toast.makeText(getApplicationContext(),"학교를 입력하세요.",Toast.LENGTH_SHORT).show();

            et_signup_school.setText("");
            et_signup_school.requestFocus();
        }
        number++;
    }
    public void check_phone(String phone){
        if(phone == ""){
            Toast.makeText(getApplicationContext(),"전화번호를 입력하세요.",Toast.LENGTH_SHORT).show();

            et_signup_phone.setText("");
            et_signup_phone.requestFocus();
        }
        number++;
    }
    //파이어베이스 인증 이용하여 회원가입
    public void signUp(final String name, final String email, final String school, final String phone){

        databaseReference.child("user").push().getKey();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){//회원가입 성공 시 데이터베이스에 저장
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String uid = user.getUid();

                    UserData userData = new UserData(uid, name, email, school, phone);
                    userData.setKVs();

                    HashMapConvertor hashMapConvertor = new HashMapConvertor(userData.getKeys(), userData.getValues());

                    DataBaseManager dataBaseManager = new DataBaseManager();
                    dataBaseManager.writeNewUser(hashMapConvertor.getHashMap());
                    Toast.makeText(getApplicationContext(),"회원가입에 성공했습니다.",Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),"회원가입에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
