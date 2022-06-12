package com.example.shade;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoticeAdapter extends BaseAdapter {
    Context context;
    ArrayList<Notice> list;

    public NoticeAdapter(Context context, ArrayList<Notice> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = View.inflate(context, R.layout.item_notice, null);
        }
        TextView title = view.findViewById(R.id.notice_title);
        TextView date = view.findViewById(R.id.notice_date);

        title.setText(list.get(i).title);
        date.setText(list.get(i).date);

        return view;
    }
}
