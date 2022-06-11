package com.example.shade;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    EditText loginPhone, loginPw;
    Button btnLogin, btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        loginPhone = findViewById(R.id.loginPhone);
        loginPw = findViewById(R.id.loginPw);
        btnLogin = findViewById(R.id.btnLogin);
        btnJoin = findViewById(R.id.btnJoin);

        // 자동로그인
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        String loginTel = sharedPreferences.getString("inputTel", null);
        String loginPwd = sharedPreferences.getString("inputPwd", null);
        String birth = sharedPreferences.getString("inputBirth", null);

        if(loginTel != null && loginPwd != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("tel", loginTel);
            startActivity(intent);
            finish();
        }

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
                mDatabase.child("users").child(phone).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getValue(User.class) != null){
                            User post = snapshot.getValue(User.class);
                            Log.w("FirebaseData", "getData" + post.toString());

                            if(post.getPwd().equals(pw)) {
                                SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor autoLogin = sharedPreferences.edit();
                                autoLogin.putString("inputTel", phone);
                                autoLogin.putString("inputPwd", pw);
                                autoLogin.putString("inputNickName", post.getUser_nick());

                                autoLogin.commit();

                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                // 메인화면으로 이동
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "비밀번호를 다시 입력하세요", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "가입되지 않은 번호입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}