package com.example.shade;

import java.security.PublicKey;

// 테이블에 들어갈 속성값을 넣을 파일
public class User {
    String phone;
    String pwd;
    String birth;
    String school;

    String user_nick;

    public User(String user_nick){}

    // 값을 추가할 때 사용
    public User(String phone, String pwd, String birth, String school, String user_nick){
        this.phone = phone;
        this.pwd = pwd;
        this.birth = birth;
        this.school = school;
        this.user_nick = user_nick;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    @Override
    public String toString(){
        return "user{" +
                "phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", birth='" + birth + '\'' +
                ", school='" + school + '\'' +
                ", nickname='" + user_nick + '\'' +
                '}';
    }

    public User() {
        String phone="";
        String pwd="";
        String birth="";
        String school="";
        String user_nick="";
    }
}
