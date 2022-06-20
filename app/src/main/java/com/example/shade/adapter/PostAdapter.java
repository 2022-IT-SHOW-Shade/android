package com.example.shade.adapter;

import android.content.Context;
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
        final Context context = viewGroup.getContext();
/*
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_posts, viewGroup, false);
        }
        TextView title = view.findViewById(R.id.post_title);
        TextView content = view.findViewById(R.id.post_content);
        TextView nickname = view.findViewById(R.id.post_nickname);
        TextView likecnt = view.findViewById(R.id.postlikeCount);
        TextView chatcnt = view.findViewById(R.id.postchatCount);
        final CheckBox checked = view.findViewById(R.id.post_like_check);
        ImageView chat = view.findViewById(R.id.postChat);
        checked.setChecked(false);

        title.setText(postItemList.get(i).getTitle());
        content.setText(postItemList.get(i).getContent());
        nickname.setText(postItemList.get(i).getUser_nick());
        //likecnt.setText(postItemList.get(i).getLike_cnt());
        //chatcnt.setText(postItemList.get(i).getComment_cnt());
        chat.setImageResource(R.drawable.chat);

        detail = view.findViewById(R.id.post_item);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PostDatailActivity.class);
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        
        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked.isChecked()){
                    // 찜하기 메소드 필요
                    checked.setChecked(true);
                }else {
                    
                    checked.setChecked(false);
                }
            }
        });
*/
        return view;
    }
}
