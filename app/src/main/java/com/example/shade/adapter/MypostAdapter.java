package com.example.shade.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shade.view.PostDatailActivity;
import com.example.shade.R;
import com.example.shade.view.WritePostActivity;
import com.example.shade.model.Post;

import java.util.ArrayList;

public class MypostAdapter extends RecyclerView.Adapter<MypostAdapter.ViewHolder> {

    ArrayList<Post> listViewItemList = new ArrayList<>();
    Context context;

    public MypostAdapter(ArrayList<Post> post, Context context) {
        listViewItemList = post;
        this.context = context;
    }

    @NonNull
    @Override
    public MypostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_my_posts, viewGroup, false);

        return new MypostAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = listViewItemList.get(position);

        // 화면에 데이터 담기
        holder.setItem(post);

        // 체크박스 해제 버그 고침
        holder.deleteCheck.setOnCheckedChangeListener(null);
        holder.deleteCheck.setChecked(post.isSelected());
        holder.deleteCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                post.setSelected(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listViewItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView item_mypost;
        TextView my_title;
        TextView my_content;
        TextView my_chatCount;
        ImageButton btnEdit;
        ImageButton btnDelete;
        CheckBox deleteCheck;
        Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            item_mypost = (CardView) itemView.findViewById(R.id.item_mypost);
            my_title = (TextView) itemView.findViewById(R.id.my_title);
            my_content = (TextView) itemView.findViewById(R.id.my_content);
            my_chatCount = (TextView) itemView.findViewById(R.id.my_chatCount);
            btnDelete = (ImageButton) itemView.findViewById(R.id.delete);
            btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);
            deleteCheck = (CheckBox) itemView.findViewById(R.id.deleteCheck);
            this.context = context;

        }

        public void setItem(Post post){
            my_title.setText(post.getTitle());
            my_content.setText(post.getContent());
            my_chatCount.setText(String.valueOf(post.getComment_cnt()));

            item_mypost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PostDatailActivity.class);
                    intent.putExtra("post_num", post.getPost_num());
                    (context).startActivity(intent);
                }
            });

            // 글 수정하기
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WritePostActivity.class);
                    intent.putExtra("post_num", post.getPost_num());
                    intent.putExtra("post_title", post.getTitle());
                    intent.putExtra("post_content", post.getContent());
                    (context).startActivity(intent);
                }
            });

            deleteCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int pos = getAdapterPosition();
                    post.setSelected(deleteCheck.isChecked());
                    listViewItemList.get(pos).setSelected(deleteCheck.isChecked());
                }
            });
        }
    }
}
