package com.example.shade.view;

import com.example.shade.User;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {
    String post_num; //primary
    String user_nick; //foreign

    String title;
    String content;
    String date;
    int like_cnt;
    int comment_cnt;
    public Map<String, Boolean> like = new HashMap<>();


    public Post(){}

    public Post(String user_nick, String title, String content) {}

    public Post(String post_num, String user_nick, String title, String content, String date) {
        this.post_num = post_num;
        this.user_nick = user_nick;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getPost_num() {
        return post_num;
    }

    public void setPost_num(String post_num) {
        this.post_num = post_num;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public int getComment_cnt() {
        return comment_cnt;
    }

    public void setComment_cnt(int comment_cnt) {
        this.comment_cnt = comment_cnt;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("post_num", post_num);
        result.put("user_nick", user_nick);
        result.put("title", title);
        result.put("content", content);
        result.put("like_cnt", like_cnt);
        result.put("comment_cnt", comment_cnt);
        result.put("like", like);

        return result;
    }



}
