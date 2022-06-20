package com.example.shade.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shade.R;

public class VersionActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar tb;
    TextView title;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        tb = findViewById(R.id.toolbar_version);
        title = findViewById(R.id.toolbar_title);
        title.setText("버전 정보");
        btnBack = findViewById(R.id.btnback);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
