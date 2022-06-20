package com.example.shade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shade.model.Comment;
import com.example.shade.R;

import java.util.ArrayList;

public class CommentAdpater extends BaseAdapter {

    ArrayList<Comment> listViewItemList = new ArrayList<>();

    TextView nickname, comment;

    public CommentAdpater(){

    }

    public CommentAdpater(ArrayList<Comment> list){
        listViewItemList = list;
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
            convertView = inflater.inflate(R.layout.item_comments, parent, false);
        }

        nickname = (TextView) convertView.findViewById(R.id.nickname);
        comment = (TextView) convertView.findViewById(R.id.comment);

        Comment comment_list = listViewItemList.get(position);

        nickname.setText(comment_list.getNickname());
        comment.setText(comment_list.getComment());

        return convertView;
    }
}
