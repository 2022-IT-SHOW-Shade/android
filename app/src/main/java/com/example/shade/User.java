package com.example.shade;

import java.security.PublicKey;

// 테이블에 들어갈 속성값을 넣을 파일
public class User {
    String phone;
    String pwd;

    public User(){}

    // 값을 추가할 때 사용
    public User(String phone, String pwd){
        this.phone = phone;
        this.pwd = pwd;
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

    @Override
    public String toString(){
        return "user{" +
                "phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
