package com.example.shade.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.shade.DeleteActivity;
import com.example.shade.MypostAdapter;
import com.example.shade.R;
import com.example.shade.RecyclerDecoration;
import com.example.shade.TimeLineAdapter;
import com.example.shade.WritePostActivity;
import com.example.shade.view.Post;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyWriteFragment extends Fragment {

    ImageButton btnWrite, btnDelete;
    CheckBox deleteCheck;
    RecyclerView contents_write;

    MypostAdapter adapter;
    ArrayList<Post> respone = new ArrayList<>();

    private DatabaseReference databaseReference;

    RecyclerDecoration recyclerDecoration;

    SwipeRefreshLayout swipeRefreshLayout;

    String num, title, content;
    long like_cnt, chat_cnt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_my_write, container, false);

        btnDelete = view.findViewById(R.id.delete);
        btnWrite = view.findViewById(R.id.btnWrite);
        contents_write = view.findViewById(R.id.contents_write);
        recyclerDecoration = new RecyclerDecoration(60);
        contents_write.addItemDecoration(recyclerDecoration);
        deleteCheck = view.findViewById(R.id.deleteCheck);

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

        databaseReference.child("posts").orderByChild("date").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.w("firebaseData", "Key : " + snapshot.getKey());

                if(snapshot.getValue(Post.class) != null){
                    Post post = snapshot.getValue(Post.class);
                    Log.w("FirebaseData", "getMyWrite" + post.toString());


                    if(tel.equals(post.getTel())) {
                        // 각각의 데이터
                         num = post.getPost_num();
                         title = post.getTitle();
                         content = post.getContent().split("\n")[0];
                         like_cnt = post.getLike_cnt();
                         chat_cnt = post.getComment_cnt();

                        respone.add(0, new Post(num, title, content, like_cnt, chat_cnt));

                        // 리스트뷰 띄우기
                        adapter = new MypostAdapter(respone, getContext());
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_bamboo);
                dialog.show();

                Button btnBambooCancel = (Button) dialog.findViewById(R.id.btnBambooCancel);
                Button btnBambooOk = (Button) dialog.findViewById(R.id.btnBambooOk);

                btnBambooOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "대나무숲으로 이동합니다.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();;
                        Intent intent = new Intent(getActivity(), DeleteActivity.class);
                        String data = "";
                        for (int i = 0; i < respone.size(); i++) {
                            Post mPost = respone.get(i);
                            if (mPost.isSelected() == true){
                                data = mPost.getPost_num().toString();
                                intent.putExtra("post_num", mPost.getPost_num());
                            }
                        }
                        startActivity(intent);
                    }
                });

                btnBambooCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });


        return view;
    }
}