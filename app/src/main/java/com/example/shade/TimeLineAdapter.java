package com.example.shade;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shade.fragment.TimeLineFragment;
import com.example.shade.view.Post;

import java.util.ArrayList;
import java.util.Collections;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {

    ArrayList<Post> listViewItemList = new ArrayList<>();
    Context context;

    public TimeLineAdapter(ArrayList<Post> post, Context context) {
        listViewItemList = post;
        this.context = context;

        for(Post str : listViewItemList){
            System.out.println("Adapter : " + str);
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
        TextView post_title, post_content, post_nickname, likeCount, chatCount;
        Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.post_card_view);
            post_title = (TextView) itemView.findViewById(R.id.post_title);
            post_content = (TextView) itemView.findViewById(R.id.post_content);
            post_nickname = (TextView) itemView.findViewById(R.id.post_nickname);
            likeCount = (TextView) itemView.findViewById(R.id.postlikeCount);
            chatCount = (TextView) itemView.findViewById(R.id.postchatCount);
            this.context = context;

        }

        public void setItem(Post post){
            post_title.setText(post.getTitle());
            post_content.setText(post.getContent());
            post_nickname.setText(post.getUser_nick());
            likeCount.setText(String.valueOf(post.getLike_cnt()));
            chatCount.setText(String.valueOf(post.getComment_cnt()));

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = getAdapterPosition();

                    Intent intent = new Intent(context, PostDatailActivity.class);
                    intent.putExtra("post_num", post.getPost_num());
                    (context).startActivity(intent);
                }
            });
        }
    }
}
