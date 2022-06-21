package com.example.shade.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shade.R;
import com.example.shade.adapter.CommentAdpater;
import com.example.shade.model.Comment;
import com.example.shade.model.Post;
import com.example.shade.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class PostDatailActivity extends AppCompatActivity {

    // 파이어베이스
    private DatabaseReference mDatabase;

    String post_num = "";
    public static String format = "yyyy/MM/dd kk:mm:ss";

    private DatabaseReference databaseReference;

    TextView detail_title, detail_nickname, detail_date, detail_content, detail_likeCount, detail_chatCount;
    ImageButton btnback, btnComment;
    ListView commentlist;
    EditText comment_et;

    Random random = new Random();

    ArrayList<Comment> list = new ArrayList<>();
    CommentAdpater adpater;

    long comment_cnt = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_detail);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent_page = getIntent();
        post_num = intent_page.getStringExtra("post_num");

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        String tel = sharedPreferences.getString("inputTel", null);

        detail_title = (TextView) findViewById(R.id.detail_title);
        detail_nickname = (TextView) findViewById(R.id.detail_nickname);
        detail_date = (TextView) findViewById(R.id.detail_date);
        detail_content = (TextView) findViewById(R.id.detail_content);
        // detail_likeCount = (TextView) findViewById(R.id.detail_likeCount);
        detail_chatCount = (TextView) findViewById(R.id.detail_chatCount);
        btnback = (ImageButton) findViewById(R.id.btnBackWrite);
        commentlist = (ListView) findViewById(R.id.commentlist);
        comment_et = (EditText) findViewById(R.id.comment_et);
        btnComment = (ImageButton) findViewById(R.id.btnComment);
        btnback = (ImageButton) findViewById(R.id.btnBackWrite);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 댓글 달기
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 댓글 내용
                String user_comment = comment_et.getText().toString();

                // 작성 날짜, 시간
                Date post_date = Calendar.getInstance().getTime();
                SimpleDateFormat format1 = new SimpleDateFormat(format, Locale.getDefault());
                String date = format1.format(post_date);

                // 댓글 번호
                String r1 = String.valueOf((char) ((int) (random.nextInt(26))+65));
                String r2 = String.valueOf((char) ((int) (random.nextInt(26))+65));
                String r3 = String.valueOf((char) ((int) (random.nextInt(26))+65));
                String r4 = String.valueOf((char) ((int) (random.nextInt(26))+65));
                String r5 = String.valueOf((char) ((int) (random.nextInt(26))+65));

                String comm_num = (String.valueOf(r1 + r2 + r3 + r4 + r5)).toString();

                addComment(comm_num, post_num, tel, user_comment, date);

                comment_et.setText("");

                // postsDB - 댓글 수 업데이트
                mDatabase.child("comment").child(post_num).child(tel).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        comment_cnt = snapshot.getChildrenCount();
                        System.out.println("com : " + comment_cnt);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Map<String, Object> taskMap = new HashMap<String, Object>();
                taskMap.put(post_num+"/comment_cnt", comment_cnt);
                databaseReference.child("posts").updateChildren(taskMap);

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();

        // 글 가져오기
        databaseReference.child("posts").child(post_num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getValue(Post.class) != null){
                    Post post = snapshot.getValue(Post.class);
                    //Log.w("FirebaseData", "getDetail" + post.toString());

                    // 각각의 데이터
                    String title = post.getTitle();
                    String nickname = post.getUser_nick();
                    String date = post.getDate();
                    String content = post.getContent();
                   // long like_cnt = post.getLike_cnt();
                    long chat_cnt = post.getComment_cnt();

                    detail_title.setText(title);
                    detail_nickname.setText(nickname);
                    detail_date.setText(date);
                    detail_content.setText(content);
                    // detail_likeCount.setText(String.valueOf(like_cnt));
                    detail_chatCount.setText(String.valueOf(chat_cnt));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // 댓글 가져오기
        mDatabase.child("comments").child(post_num).child(tel).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getValue(Comment.class) != null){
                    Comment comment = snapshot.getValue(Comment.class);
                    //Log.w("FirebaseData", "getDetail" + comment.toString());

                    // 각각의 데이터
                    String comm = comment.getComment();
                    String com_date = comment.getCom_date();

                    mDatabase.child("users").child(tel).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String nickname = snapshot.getValue(User.class).getUser_nick();
                            System.out.println(nickname);

                            list.add(new Comment(comm, com_date, nickname));

                            adpater = new CommentAdpater(list);
                            commentlist.setAdapter(adpater);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addComment(String comm_num, String post_num, String tel, String user_comment, String date) {
        Comment comment = new Comment(post_num, tel, user_comment, date);

        //databaseReference.child("comments").removeValue();

        mDatabase.child("comments").child(post_num).child(tel).child(comm_num).setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

        mDatabase.child("posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getValue(Post.class) != null){
                    Post post = snapshot.getValue(Post.class);
                    //Log.w("FirebaseData", "getData" + post.toString());

                    if(post_num.equals(post.getPost_num())){
                        long comm_cnt = snapshot.getValue(Post.class).getComment_cnt() + 1;

                        Map<String, Object> taskMap = new HashMap<String, Object>();
                        taskMap.put(post_num+"/comment_cnt", comm_cnt);
                        mDatabase.child("posts").updateChildren(taskMap);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
