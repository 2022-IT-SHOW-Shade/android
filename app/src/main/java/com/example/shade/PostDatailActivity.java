package com.example.shade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shade.view.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class PostDatailActivity extends AppCompatActivity {

    String post_num = "";

    private DatabaseReference databaseReference;

    TextView detail_title, detail_nickname, detail_date, detail_content, detail_likeCount, detail_chatCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_detail);

        Intent intent_page = getIntent();
        post_num = intent_page.getStringExtra("post_num");

        detail_title = (TextView) findViewById(R.id.detail_title);
        detail_nickname = (TextView) findViewById(R.id.detail_nickname);
        detail_date = (TextView) findViewById(R.id.detail_date);
        detail_content = (TextView) findViewById(R.id.detail_content);
        detail_likeCount = (TextView) findViewById(R.id.detail_likeCount);
        detail_chatCount = (TextView) findViewById(R.id.detail_chatCount);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("posts").child(post_num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getValue(Post.class) != null){
                    Post post = snapshot.getValue(Post.class);
                    Log.w("FirebaseData", "getDetail" + post.toString());

                    // 각각의 데이터
                    String title = post.getTitle();
                    String nickname = post.getUser_nick();
                    String date = post.getDate();
                    String content = post.getContent();
                    long like_cnt = post.getLike_cnt();
                    long chat_cnt = post.getComment_cnt();

                    detail_title.setText(title);
                    detail_nickname.setText(nickname);
                    detail_date.setText(date);
                    detail_content.setText(content);
                    detail_likeCount.setText(String.valueOf(like_cnt));
                    detail_chatCount.setText(String.valueOf(chat_cnt));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
