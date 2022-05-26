package com.example.shade;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import kr.go.neis.api.NEISException;

public class SearchSchoolActivity extends AppCompatActivity {

    SearchView search_school;
    ListView search_school_list;
    TextView resultTextView;

    List<School> list;
   // List<String> items = Arrays.asList("어벤져스", "배트맨", "배트맨2", "배구", "슈퍼맨");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_school);


        search_school = findViewById(R.id.search_school);
        //search_school_list = findViewById(R.id.search_school_list);
        resultTextView = findViewById(R.id.textView);

        search_school.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                SchoolAPI api = new SchoolAPI();
                try {
                    list = api.getSchoolByName(query);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // resultTextView.setText(search(newText));
                return true;
            }
        });

    }

    private  String search(String query){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < list.size(); i++){
            String item = list.get(i).toString();
            if(item.toLowerCase().contains(query.toLowerCase())){
                sb.append(item);
                if(i != list.size() - 1){
                    sb.append("\n");
                }
            }
        }

        return sb.toString();
    }

    private String getResult(){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < list.size(); i++){
            String item = list.get(i).toString();
            sb.append(item);
            if(i != list.size() - 1){
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
