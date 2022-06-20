package com.example.shade.model;

public class Comment {

    String post_num = "";
    String phone = "";
    String comment = "";
    String com_date = "";

    // 댓글 리스트뷰 때 사용할 변수
    String nickname = "";

    public Comment(){}

    public Comment(String post_num, String phone, String comment, String com_date) {
        this.post_num = post_num;
        this.phone = phone;
        this.comment = comment;
        this.com_date = com_date;
    }

    public Comment(String comm, String com_date, String nickname) {
        this.comment = comm;
        this.com_date = com_date;
        this.nickname = nickname;
    }

    public String getPost_num() {
        return post_num;
    }

    public void setPost_num(String post_num) {
        this.post_num = post_num;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCom_date() {
        return com_date;
    }

    public void setCom_date(String com_date) {
        this.com_date = com_date;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "post_num='" + post_num + '\'' +
                ", phone='" + phone + '\'' +
                ", comment='" + comment + '\'' +
                ", com_date='" + com_date + '\'' +
                '}';
    }
}
