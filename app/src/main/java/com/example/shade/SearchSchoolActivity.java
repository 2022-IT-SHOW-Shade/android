package com.example.shade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import kr.go.neis.api.NEISException;

public class SearchSchoolActivity extends AppCompatActivity {

    SearchView search_school;
    ListView search_school_list;

    ArrayList<School> list = new ArrayList<>();

    ListViewAdapter adapter;
    boolean check = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_school);


        search_school = findViewById(R.id.search_school);
        search_school_list = findViewById(R.id.search_school_list);

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

                //System.out.println(school_name);

                // 회원가입 창으로 돌아가기
                Intent intent = new Intent();
                intent.putExtra("school", school_name);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    public void showAdapter(ArrayList<School> list){
        adapter = new ListViewAdapter(list);
        search_school_list.setAdapter(adapter);
    }
}
