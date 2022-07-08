package com.example.shade.view.server;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shade.view.MainActivity;
import com.example.shade.R;
import com.example.shade.adapter.SchoolAdapter;
import com.example.shade.model.Post;
import com.example.shade.model.School;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

public class SearchSchoolActivity extends AppCompatActivity {

    SearchView search_school;
    ListView search_school_list;

    ArrayList<School> list = new ArrayList<>();

    SchoolAdapter adapter;
    String page = "Join";

    // 파이어베이스
    private DatabaseReference databaseReference;

    androidx.appcompat.widget.Toolbar tb;
    TextView title;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_school);

        search_school = findViewById(R.id.search_school);
        search_school_list = findViewById(R.id.search_school_list);
        tb = findViewById(R.id.toolbar_search);
        title = findViewById(R.id.toolbar_title);
        title.setText("학교 등록");
        btnBack = findViewById(R.id.btnback);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent_page = getIntent();
        page = intent_page.getStringExtra("page");

        if(page == null)
            page = "join";

        search_school.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SchoolAPI api = new SchoolAPI();

                new Thread(() -> {

                    try {
                        list = api.getSchoolByName(query);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                }).start();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                showAdapter(list);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        search_school_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String school_name = (String) adapterView.getAdapter().getItem(i);
                String school_address = adapter.getAddress(i);

                if(page.equals("mypage")){
                    // 마이페이지 창으로 돌아가기
                    System.out.println(school_name + "   " + school_address);

                    // 정보 업데이트
                    updateSchool(school_name, school_address);

                    // 돌아가기
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "학교가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                }else if(page.equals("join")) {
                    // 회원가입 창으로 돌아가기
                    Intent intent = new Intent();
                    intent.putExtra("school", school_name);
                    intent.putExtra("address", school_address);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    public void showAdapter(ArrayList<School> list){
        adapter = new SchoolAdapter(list);
        search_school_list.setAdapter(adapter);
    }

    public void updateSchool(String name, String address){

        databaseReference = FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        String loginTel = sharedPreferences.getString("inputTel", null);
        String nickname = sharedPreferences.getString("inputNickName", null);

        String new_nickname;

        Map<String, Object> taskMap = new HashMap<String, Object>();
        taskMap.put(loginTel+"/school", name);

        // 닉네임
        // 지역구
        String[] strArr = address.split(" ");
        new_nickname = strArr[1] + " ";

        // 초 중 고
        final char c = name.charAt(name.length() - 4);
        if(c != '초' && c != '고')
            new_nickname += "중";
        else new_nickname += name.substring(name.length() - 4, name.length() - 3);

        new_nickname += nickname.substring(nickname.length() - 1, nickname.length());


        taskMap.put(loginTel+"/user_nick", new_nickname);

        // 데이터 업데이트
        databaseReference.child("users").updateChildren(taskMap);

        // 디바이스에 정보 업데이트
        SharedPreferences.Editor autoLogin = sharedPreferences.edit();
        autoLogin.putString("inputNickName", new_nickname);
        autoLogin.commit();

        // posts - 학교, 닉네임 변경
        String finalNew_nickname = new_nickname;
        databaseReference.child("posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.w("firebaseData", "Key : " + snapshot.getKey());

                if(snapshot.getValue(Post.class) != null){
                    Post post = snapshot.getValue(Post.class);
                    Log.w("FirebaseData", "getData" + post.toString());

                    if(loginTel.equals(post.getTel())){
                        Map<String, Object> taskMap_p = new HashMap<String, Object>();
                        taskMap_p.put(snapshot.getKey()+ "/school", name);
                        taskMap_p.put(snapshot.getKey() + "/user_nick", finalNew_nickname);

                        databaseReference.child("posts").updateChildren(taskMap_p);
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
