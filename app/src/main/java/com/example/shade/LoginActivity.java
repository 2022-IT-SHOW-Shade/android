package com.example.shade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText loginPhone, loginPw;
    Button btnLogin, btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPhone = findViewById(R.id.loginPhone);
        loginPw = findViewById(R.id.loginPw);
        btnLogin = findViewById(R.id.btnLogin);
        btnJoin = findViewById(R.id.btnJoin);

        // 회원가입 버튼 클릭 시 창 이동
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        // 로그인 버튼 클릭 시 정보 확인
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = loginPhone.getText().toString();
                String pw = loginPw.getText().toString();

                // DB에서 같은 값이 있는지 확인
                // 같으면 메인화면으로 넘어가고 없으면 틀렸다는 문구 출력
            }
        });

    }
}