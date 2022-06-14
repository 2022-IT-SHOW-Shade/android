package com.example.shade.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shade.R;
import com.example.shade.TimeLineAdapter;
import com.example.shade.WritePostActivity;
import com.example.shade.view.Post;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyWriteFragment extends Fragment {

    ImageButton btnWrite;

    RecyclerView contents_write;

    TimeLineAdapter adapter;
    ArrayList<Post> respone = new ArrayList<>();

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_my_write, container, false);

        btnWrite = view.findViewById(R.id.btnWrite);
        contents_write = view.findViewById(R.id.contents_write);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WritePostActivity.class);
                startActivity(intent);
            }
        });

        // 내가 쓴 글 가져오기
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        String tel = sharedPreferences.getString("inputTel", null);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.w("firebaseData", "Key : " + snapshot.getKey());

                if(snapshot.getValue(Post.class) != null){
                    Post post = snapshot.getValue(Post.class);
                    Log.w("FirebaseData", "getMyWrite" + post.toString());


                    if(tel.equals(post.getTel())) {
                        // 각각의 데이터
                        String num = post.getPost_num();
                        String title = post.getTitle();
                        String content = post.getContent().split("\n")[0];
                        String nickname = post.getUser_nick();
                        long like_cnt = post.getLike_cnt();
                        long chat_cnt = post.getComment_cnt();

                        respone.add(0, new Post(num, title, content, nickname, like_cnt, chat_cnt));

                        // 리스트뷰 띄우기
                        adapter = new TimeLineAdapter(respone, getContext());
                        contents_write.setAdapter(adapter);
                        contents_write.setLayoutManager(new LinearLayoutManager(getContext()));
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

        return view;
    }
}