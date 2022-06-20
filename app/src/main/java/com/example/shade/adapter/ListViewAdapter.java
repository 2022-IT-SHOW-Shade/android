package com.example.shade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shade.R;
import com.example.shade.model.School;

import java.util.ArrayList;


public class ListViewAdapter extends BaseAdapter {

    ArrayList<School> listViewItemList = new ArrayList<>();

    TextView school_name, school_address;

    public ListViewAdapter(){

    }

    public ListViewAdapter(ArrayList<School> list){
        listViewItemList = list;
        System.out.println("li : " + list);
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position).getName();
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
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        school_name = (TextView) convertView.findViewById(R.id.sc_name);
        school_address = (TextView) convertView.findViewById(R.id.sc_address);

        School school_list = listViewItemList.get(position);

        school_name.setText(school_list.getName());
        school_address.setText(school_list.getLocation());

        return convertView;
    }

    public String getAddress(int position){
        return listViewItemList.get(position).getLocation();
    }

}
