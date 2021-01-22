package com.example.teamdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email;
    private EditText et_password;
    private ImageButton btn_signup;
    private ImageButton btn_signin;
    private InputMethodManager imm;

    private String email;
    private String password;

    //파이어베이스 인증 관리자
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_signin = findViewById(R.id.btn_signin);
        btn_signup = findViewById(R.id.btn_signup);

        imm = (InputMethodManager) this.getSystemService(SignupActivity.INPUT_METHOD_SERVICE);

        //파이어베이스 인증 관리자 등록
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.btn_signup:
                Intent it = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(it);
                break;
            case R.id.btn_signin:
                //로그인 이벤트 발생
                //로그인 이메일 및 비밀번호 값을 에디트 텍스트로부터 받아옴
                email = et_email.getText().toString();
                password = et_password.getText().toString();

                //아이디 혹은 비밀번호를 입력하지 않고 버튼을 눌렀을 경우
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "아이디 혹은 비밀번호를 입력하지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else { //입력란이 공백이 아닐 경우
                    //파이어베이스 인증 과정
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //로그인 성공시
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "로그인 성공.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), TeamSelectActivity.class);
                                startActivity(intent);
                            } else { //로그인 실패 시
                                Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                                et_email.setText("");
                                et_password.setText("");
                            }
                        }
                    });
                }
                break;
            case R.id.lo_signin:
                //키보드 내리기
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }
    }
}
