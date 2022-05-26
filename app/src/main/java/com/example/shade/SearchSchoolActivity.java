package com.example.shade;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import kr.go.neis.api.NEISException;
import kr.go.neis.api.School;

public class SearchSchoolActivity extends AppCompatActivity {

    SearchView search_school;
   // List<String> items = Arrays.asList("어벤져스", "배트맨", "배트맨2", "배구", "슈퍼맨");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_school);


        search_school = findViewById(R.id.search_school);



        search_school.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
    }
/*
    private  String search(String query){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < items.size(); i++){
            String item = items.get(i);
            if(item.toLowerCase().contains(query.toLowerCase())){
                sb.append(item);
                if(i != items.size() - 1){
                    sb.append("\n");
                }
            }
        }

        return sb.toString();
    }

    private String getResult(){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < items.size(); i++){
            String item = items.get(i);
            sb.append(item);
            if(i != items.size() - 1){
                sb.append("\n");
            }
        }

        return sb.toString();
    }*/
}
