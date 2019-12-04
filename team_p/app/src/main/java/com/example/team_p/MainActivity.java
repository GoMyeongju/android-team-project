package com.example.team_p;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText et_login_email;
    private EditText et_login_pwd;

    private String login_email;
    private String login_pwd;

    InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        et_login_email = findViewById(R.id.et_email);
        et_login_pwd = findViewById(R.id.et_password);

    }

    //로그인 버튼 눌렀을 때
    public void login(View view) {

        //로그인 이메일 및 비밀번호 값을 에디트 텍스트로부터 받아옴
        login_email = et_login_email.getText().toString();
        login_pwd = et_login_pwd.getText().toString();

        //아이디 혹은 비밀번호를 입력하지 않고 버튼을 눌렀을 경우
        if (login_email.isEmpty() || login_pwd.isEmpty()) {
            Toast.makeText(MainActivity.this, "아이디 혹은 비밀번호를 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
        } else { //입력란이 공백이 아닐 경우
            //파이어베이스 인증 과정
            firebaseAuth.signInWithEmailAndPassword(login_email, login_pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //로그인 성공시
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "로그인 성공.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Item_list.class);
                        startActivity(intent);
                        //et_login_email.setText("");
                        //et_login_pwd.setText("");
                    } else { //로그인 실패 시
                        Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        et_login_email.setText("");
                        et_login_pwd.setText("");
                    }

                }
            });
        }
    }

    //회원가입 버튼이 눌렸을 때
    public void join(View view){
        Intent intent = new Intent(getApplicationContext(), Join.class);
        startActivity(intent);
    }


    public void keyboard_down(View view){
        imm.hideSoftInputFromWindow(et_login_email.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et_login_pwd.getWindowToken(), 0);
    }
}
