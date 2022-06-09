package com.example.shade;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shade.view.Post;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WritePostActivity extends AppCompatActivity {
    private static final String TAG = "WritePostActivity";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = database.getReference("users");

    EditText editTitle, editContent;
    Button btnFinish;
    Toolbar toolbarWrite;
    ImageButton btnBack;
    String date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_detail);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnFinish = findViewById(R.id.btn_finish);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTitle.length() > 0 && editContent.length() > 0){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
                    Date strdate = new Date(System.currentTimeMillis());
                    date = dateFormat.format(strdate);

                    mDatabase.setValue(editTitle.getText().toString(), editContent.getText().toString());
                }
                else if(editTitle.length() > 0){
                    Toast.makeText(getApplicationContext(), "제목을 작성해주세요", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "글을 작성해주세요", Toast.LENGTH_SHORT).show();
            }
        });


        toolbarWrite = findViewById(R.id.toolbar_write);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addPost(String post_num, String user_nick, String title, String content, String date){
        Post post = new Post(post_num, user_nick, title, content, date);
        mDatabase.child("users").child(user_nick).setValue(post);
    }
}
