package com.example.shade.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shade.view.PostDatailActivity;
import com.example.shade.R;
import com.example.shade.model.Post;

import java.util.ArrayList;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {

    ArrayList<Post> listViewItemList = new ArrayList<>();
    Context context;

    public TimeLineAdapter(ArrayList<Post> post, Context context) {
        listViewItemList = post;
        this.context = context;

        for(Post str : listViewItemList){
            Log.i("tag","Adapter : " + str);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_posts, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = listViewItemList.get(position);

        // 화면에 데이터 담기
        holder.setItem(post);
    }

    @Override
    public int getItemCount() {
        return listViewItemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView postTitle;
        TextView postContent;
        TextView postNickname;
        TextView chatCount;
        Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.post_card_view);
            postTitle = (TextView) itemView.findViewById(R.id.post_title);
            postContent = (TextView) itemView.findViewById(R.id.post_content);
            postNickname = (TextView) itemView.findViewById(R.id.post_nickname);
            chatCount = (TextView) itemView.findViewById(R.id.postchatCount);
            this.context = context;

        }

        public void setItem(Post post){
            postTitle.setText(post.getTitle());
            postContent.setText(post.getContent());
            postNickname.setText(post.getUser_nick());
            chatCount.setText(String.valueOf(post.getComment_cnt()));

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PostDatailActivity.class);
                    intent.putExtra("post_num", post.getPost_num());
                    (context).startActivity(intent);
                }
            });
        }
    }
}
