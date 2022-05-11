package com.example.shade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinActivity extends AppCompatActivity {

    // 파이어베이스
    private DatabaseReference mDatabase;

    EditText loginPhone, loginPw, loginPwCheck;
    Button btnJoinOk;

    String phone, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        loginPhone = findViewById(R.id.loginPhone);
        loginPw = findViewById(R.id.loginPw);
        loginPwCheck = findViewById(R.id.loginPwCheck);
        btnJoinOk = findViewById(R.id.btnJoinOk);

        // 완료 버튼 클릭시 값 저장, 비밀번호 체크
        btnJoinOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 중복 전화번호 체크
                mDatabase.child("users").child(loginPhone.getText().toString()).child("phone").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);

                        if(value != null){
                            Toast.makeText(getApplicationContext(), "이미 가입된 번호입니다.", Toast.LENGTH_SHORT).show();
                        }else{
                            // 재입력한 비밀번호가 맞는지 확인
                            String loginPwd = loginPw.getText().toString();
                            String CheckPwd = loginPwCheck.getText().toString();
                            if(!loginPwd.equals(CheckPwd)){
                                Toast.makeText(getApplicationContext(), "비밀번호가 맞지 않습니다." + loginPwd + " " + CheckPwd, Toast.LENGTH_SHORT).show();
                            }else {
                                phone = loginPhone.getText().toString();
                                pw = loginPw.getText().toString();

                                // 파이어베이스에 값 저장
                                addUser(phone, pw);

                                // 로그인 창으로 넘어가기
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void addUser(String phone, String pw){
        User user = new User(phone, pw);

        mDatabase.child("users").child(phone).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}