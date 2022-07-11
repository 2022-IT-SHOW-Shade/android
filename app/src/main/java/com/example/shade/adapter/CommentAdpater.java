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

    TextView nickname;
    TextView comment;

    public CommentAdpater(){

    }

    public CommentAdpater(ArrayList<Comment> commentArrayList){
        listViewItemList = commentArrayList;
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

        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_comments, parent, false);
        }

        nickname = (TextView) convertView.findViewById(R.id.nickname);
        comment = (TextView) convertView.findViewById(R.id.comment);

        Comment commentList = listViewItemList.get(position);

        nickname.setText(commentList.getNickname());
        comment.setText(commentList.getComment());

        return convertView;
    }
}
