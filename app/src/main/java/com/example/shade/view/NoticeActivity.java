package com.example.shade.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shade.R;
import com.example.shade.adapter.NoticeAdapter;
import com.example.shade.model.Notice;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {
    ListView list;
    NoticeAdapter noticeAdapter;
    ArrayList<Notice> arrayList;
    Notice item;

    String[] nTitle = {"올해 1.9.0 업데이트 안내", "Shade 사칭 주의", "Shade 개인정보 약관 변경 안내", "버그 업데이트 안내"};
    String[] nDate = {"22/06/02", "22/05/30", "21/09/20", "21/03/02"};

    androidx.appcompat.widget.Toolbar tb;
    TextView toolbar_title;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        list = findViewById(R.id.noticelist);
        arrayList = new ArrayList<>();

        for (int i =0; i<nTitle.length; i++){
            item = new Notice(nTitle[i], nDate[i]);
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

        tb = findViewById(R.id.toolbar_notice);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("공지사항");
        btnBack = findViewById(R.id.btnback);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
