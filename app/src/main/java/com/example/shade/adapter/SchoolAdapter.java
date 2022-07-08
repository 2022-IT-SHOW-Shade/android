package com.example.shade.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shade.R;
import com.example.shade.model.School;

import java.util.ArrayList;


public class SchoolAdapter extends BaseAdapter {

    ArrayList<School> listViewItemList = new ArrayList<>();

    TextView school_name;
    TextView school_address;

    public SchoolAdapter(){

    }

    public SchoolAdapter(ArrayList<School> list){
        listViewItemList = list;
        Log.i("tag","li : " + list);
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
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_school, parent, false);
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
