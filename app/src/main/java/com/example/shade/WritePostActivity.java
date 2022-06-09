package com.example.shade;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WritePostActivity extends AppCompatActivity {
    EditText editTitle, editContent;
    Button btnFinish;
    Toolbar toolbarWrite;
    ImageButton btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_detail);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnFinish = findViewById(R.id.btn_finish);



        toolbarWrite = findViewById(R.id.toolbar_write);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
