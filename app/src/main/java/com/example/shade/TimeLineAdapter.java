package com.example.shade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shade.view.Post;

import java.util.ArrayList;

public class TimeLineAdapter extends BaseAdapter {

    ArrayList<Post> listViewItemList = new ArrayList<>();

    TextView post_title, post_content, post_nickname, likeCount, chatCount;

    public TimeLineAdapter(ArrayList<Post> post) {
        listViewItemList = post;
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();


        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_posts, parent, false);
        }

        post_title = (TextView) convertView.findViewById(R.id.post_title);
        post_content = (TextView) convertView.findViewById(R.id.post_content);
        post_nickname = (TextView) convertView.findViewById(R.id.post_nickname);
        likeCount = (TextView) convertView.findViewById(R.id.postlikeCount);
        chatCount = (TextView) convertView.findViewById(R.id.postchatCount);

        Post post_list = listViewItemList.get(pos);

        post_title.setText(post_list.getTitle());
        post_content.setText(post_list.getContent());
        post_nickname.setText(post_list.getUser_nick());
        likeCount.setText(String.valueOf(post_list.getLike_cnt()));
        chatCount.setText(String.valueOf(post_list.getComment_cnt()));

        return convertView;
    }
}
