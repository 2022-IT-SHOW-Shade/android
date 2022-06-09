package com.example.shade.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shade.LoginActivity;
import com.example.shade.R;
import com.example.shade.SearchSchoolActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyPageFragment extends Fragment implements View.OnClickListener {

    TextView my_page_user_name, my_school;
    Button btnLike, notice, change, version, logout, withdrawal;

    // 파이어베이스
    private DatabaseReference databaseReference;

    Intent intent;

    String school, address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        my_page_user_name = (TextView) v.findViewById(R.id.my_page_user_name);  // 닉네임
        my_school = (TextView) v.findViewById(R.id.my_school); // 학교

        btnLike = (Button) v.findViewById(R.id.btnLike);    // 찜한 글
        notice = (Button) v.findViewById(R.id.notice);  // 공지사항
        change = (Button) v.findViewById(R.id.change);  // 학교 변경 및 본인 인증
        version = (Button)  v.findViewById(R.id.version);   // 앱 버전
        logout = (Button) v.findViewById(R.id.logout);  // 로그아웃
        withdrawal = (Button) v.findViewById(R.id.withdrawal);  // 탈퇴

        // 저장된 전화번호 가져오기
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        String loginTel = sharedPreferences.getString("inputTel", null);

        // 닉네임, 학교 가져오기
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Query query = databaseReference.orderByChild("phone").equalTo(loginTel);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    String nick = data.child("user_nick").getValue().toString();
                    String school = data.child("school").getValue().toString();

                    my_page_user_name.setText(nick);
                    my_school.setText(school);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        logout.setOnClickListener(this);
        change.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout:
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLike:
                break;
            case R.id.change:
                intent = new Intent(getActivity(), SearchSchoolActivity.class);
                intent.putExtra("page", "mypage");
                startActivity(intent);
                break;
        }
    }
}

