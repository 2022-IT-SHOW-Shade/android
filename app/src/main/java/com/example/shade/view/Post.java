package com.example.shade.view;

import com.example.shade.User;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {
    String post_num; //primary
    String tel; //foreign

    String title;
    String content;
    String date;
    String school;
    String user_nick;
    long like_cnt;
    long comment_cnt;

    public Post(){}

    public Post(String post_num){
        this.post_num = post_num;
    }

    public Post(String post_num, String user_nick, String title, String content, String date, String school, String tel) {
        this.post_num = post_num;
        this.user_nick = user_nick;
        this.title = title;
        this.content = content;
        this.date = date;
        this.school = school;
        this.tel = tel;
    }

    public Post(String title, String content, String nickname, long like_cnt, long chat_cnt) {
        this.title = title;
        this.content = content;
        this.user_nick = nickname;
        this.like_cnt = like_cnt;
        this.comment_cnt = chat_cnt;
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

    public long getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(long like_cnt) {
        this.like_cnt = like_cnt;
    }

    public long getComment_cnt() {
        return comment_cnt;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setComment_cnt(long comment_cnt) {
        this.comment_cnt = comment_cnt;
    }

    @Override
    public String toString(){
        return "post{" +
                "post_num='" + post_num + '\'' +
                ", tel='" + tel + '\'' +
                ", user_nick='" + user_nick + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", school='" + school + '\'' +
                '}';
    }

}
