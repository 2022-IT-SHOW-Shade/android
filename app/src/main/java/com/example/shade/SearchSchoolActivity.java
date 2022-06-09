package com.example.shade;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shade.fragment.MyPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import kr.go.neis.api.NEISException;

public class SearchSchoolActivity extends AppCompatActivity {

    SearchView search_school;
    ListView search_school_list;

    ArrayList<School> list = new ArrayList<>();

    ListViewAdapter adapter;
    String page = "Join";

    // 파이어베이스
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_school);

        search_school = findViewById(R.id.search_school);
        search_school_list = findViewById(R.id.search_school_list);

        Intent intent_page = getIntent();
        page = intent_page.getStringExtra("page");

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
                }else {
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
        adapter = new ListViewAdapter(list);
        search_school_list.setAdapter(adapter);
    }

    public void updateSchool(String name, String address){

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        String loginTel = sharedPreferences.getString("inputTel", null);

        String nickname;

        Map<String, Object> taskMap = new HashMap<String, Object>();
        taskMap.put(loginTel+"/school", name);

        // 닉네임
        // 지역구
        String[] strArr = address.split(" ");
        nickname = strArr[1] + " ";

        // 초 중 고
        final char c = name.charAt(name.length() - 4);
        if(c != '초' && c != '고')
            nickname += "중";
        else nickname += name.substring(name.length() - 4, name.length() - 3);

        System.out.println(nickname);

        taskMap.put(loginTel+"/user_nick", nickname);

    }
}
