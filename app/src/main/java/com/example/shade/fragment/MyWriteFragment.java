package com.example.shade.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shade.ListViewAdapter;
import com.example.shade.PostAdapter;
import com.example.shade.R;
import com.example.shade.WritePostActivity;
import com.example.shade.view.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyWriteFragment extends Fragment {
    DatabaseReference databaseReference;
    ListView myList;
    PostAdapter postAdapter;

    ArrayList<Post> list;

    LinearLayout layout;
    ImageButton btnWrite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_my_write, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");

        btnWrite = view.findViewById(R.id.btnWrite);
        myList = view.findViewById(R.id.mycontents_write);
        list = new ArrayList<>();

        databaseReference.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    list.add(post);
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        postAdapter = new PostAdapter(list);
        myList.setAdapter(postAdapter);


        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WritePostActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }
}
