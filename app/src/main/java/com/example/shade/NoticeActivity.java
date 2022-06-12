package com.example.shade;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {
    ListView list;
    NoticeAdapter noticeAdapter;
    ArrayList<Notice> arrayList;
    Notice item;

    String[] title = {"올해 1.9.0 업데이트 안내", "Shade 사칭 주의", "Shade 개인정보 약관 변경 안내", "버그 업데이트 안내"};
    String[] date = {"22/06/02", "22/05/30", "21/09/20", "21/03/02"};

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        list = findViewById(R.id.noticelist);
        arrayList = new ArrayList<>();

        for (int i =0; i<title.length; i++){
            item = new Notice(title[i], date[i]);
            arrayList.add(item);
        }
        noticeAdapter = new NoticeAdapter(this, arrayList);
        list.setAdapter(noticeAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Notice nItem = (Notice) list.getItemAtPosition(i);
                String title = nItem.getTitle();
                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
            }
        });

        btnBack = findViewById(R.id.mpbtnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
