package com.example.shade.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shade.R;
import com.example.shade.view.RecyclerDecoration;
import com.example.shade.adapter.TimeLineAdapter;
import com.example.shade.model.Post;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class TimeLineFragment extends Fragment {

    RecyclerView contents_timeline;

    TimeLineAdapter adapter;
    ArrayList<Post> respone = new ArrayList<>();

    private DatabaseReference databaseReference;

    RecyclerDecoration recyclerDecoration;
    androidx.recyclerview.widget.RecyclerView recyclerView;

    String num, title, content, nickname;
    long like_cnt, chat_cnt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        contents_timeline = (RecyclerView) view.findViewById(R.id.contents_timeline);
        recyclerView = view.findViewById(R.id.contents_timeline);
        recyclerDecoration = new RecyclerDecoration(60);
        recyclerView.addItemDecoration(recyclerDecoration);

        // 글쓰기 데이터 가져오기
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("posts").orderByChild("date").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //Log.w("firebaseData", "Key : " + snapshot.getKey());

                if(snapshot.getValue(Post.class) != null){
                    Post post = snapshot.getValue(Post.class);
                    //Log.w("FirebaseData", "getData" + post.toString());

                    String key = databaseReference.child("comments").child(post.getPost_num()).getKey();
                    System.out.println(key);

                    // 각각의 데이터
                     num = post.getPost_num();
                     title = post.getTitle();
                     content = post.getContent().split("\n")[0];
                     nickname = post.getUser_nick();
                     like_cnt = post.getLike_cnt();
                     chat_cnt = post.getComment_cnt();

                    System.out.println("chat : " + chat_cnt);

                    respone.add(0, new Post(num, title, content, nickname, like_cnt, chat_cnt));

                    // 리스트뷰 띄우기
                    adapter = new TimeLineAdapter(respone, getContext());
                    contents_timeline.setAdapter(adapter);
                    contents_timeline.setLayoutManager(new LinearLayoutManager(getContext()));
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
