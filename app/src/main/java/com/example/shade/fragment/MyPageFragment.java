package com.example.shade.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shade.view.userActivity.LoginActivity;
import com.example.shade.view.NoticeActivity;
import com.example.shade.R;
import com.example.shade.view.server.SearchSchoolActivity;
import com.example.shade.view.VersionActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyPageFragment extends Fragment implements View.OnClickListener {

    TextView myPageUserName;
    TextView mySchool;

    Button notice;
    Button version;
    Button change;
    Button logout;
    Button withdrawal;

    // 파이어베이스
    private DatabaseReference databaseReference;

    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        myPageUserName = (TextView) v.findViewById(R.id.my_page_user_name);  // 닉네임
        mySchool = (TextView) v.findViewById(R.id.my_school); // 학교

        notice = (Button) v.findViewById(R.id.notice);  // 공지사항
        version = (Button) v.findViewById(R.id.version); // 앱 버전
        change = (Button) v.findViewById(R.id.change);  // 학교 변경 및 본인 인증
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

                    myPageUserName.setText(nick);
                    mySchool.setText(school);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        notice.setOnClickListener(this);
        version.setOnClickListener(this);
        logout.setOnClickListener(this);
        change.setOnClickListener(this);
        withdrawal.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.notice:
                intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.version:
                intent = new Intent(getActivity(), VersionActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.change:
                intent = new Intent(getActivity(), SearchSchoolActivity.class);
                intent.putExtra("page", "mypage");
                startActivity(intent);
                break;
            case R.id.withdrawal:
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_withdrawal);
                dialog.show();

                Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
                Button btnCancle = (Button) dialog.findViewById(R.id.btnCancel);

                // 회원 탈퇴
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "탈퇴되었습니다.", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
                        String loginTel = sharedPreferences.getString("inputTel", null);
                        databaseReference.child(loginTel).removeValue();

                        dialog.dismiss();

                        // 로그아웃 및 디바이스 데이터 삭제
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("inputTel");
                        editor.remove("inputPwd");
                        editor.remove("inputNickName");
                        editor.commit();

                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                });

                btnCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            default: break;
        }
    }
}

