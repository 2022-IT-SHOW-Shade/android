package com.example.shade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {

    EditText loginPhone, loginPw, loginPwCheck;
    Button btnJoinOk;

    String phone, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        loginPhone = findViewById(R.id.loginPhone);
        loginPw = findViewById(R.id.loginPw);
        loginPwCheck = findViewById(R.id.loginPwCheck);
        btnJoinOk = findViewById(R.id.btnJoinOk);

        // 완료 버튼 클릭시 값 저장, 비밀번호 체크
        btnJoinOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 재입력한 비밀번호가 맞는지 확인
                if(loginPw != loginPwCheck){
                    Toast.makeText(getApplicationContext(), "비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    phone = loginPhone.getText().toString();
                    pw = loginPw.getText().toString();

                    // DB에 값 저장

                    // 로그인 창으로 넘어가기
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}