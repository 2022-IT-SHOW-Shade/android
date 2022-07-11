package com.example.shade.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.shade.model.Post;

import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {
    ArrayList<Post> postItemList = new ArrayList<>();
    LinearLayout detail;

    public PostAdapter(){}

    public PostAdapter(ArrayList<Post> list){
        postItemList = list;
    }


    @Override
    public int getCount() {
        return postItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return postItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        return view;
    }
}
