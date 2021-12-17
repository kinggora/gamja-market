package com.example.gamjamarket.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class WriteinfoModel2 implements Serializable {
    private String title;
    private String contents;
    private String explain1;
    private String explain2;
    private String explain3;
    private Date createdAt;
    private String category;
    private String nickname;
    private String callnumber;
    private String pid; //게시글id
    //----------여기까지 object 생성시 초기화 필요---------------

    private int likes = 0; //찜

    public WriteinfoModel2(){}

    //board1 게시물 생성자
    public WriteinfoModel2(String title, String category, String nickname, String contents, String explain1, String explain2, String explain3, String pid, String callnumber){
        this.title = title;
        this.category = category;
        this.explain1 = explain1;
        this.explain2 = explain2;
        this.explain3 = explain3;
        this.contents = contents;
        this.pid = pid;
        this.createdAt = createdAt;
        this.callnumber = callnumber;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {this.title = title;}
    public String getExplain1() {
        return explain1;
    }
    public void setExplain1(String explain) {this.explain1 = explain;}
    public String getExplain2() {
        return explain2;
    }
    public void setExplain2(String explain) {this.explain2 = explain;}
    public String getExplain3() {
        return explain3;
    }
    public void setExplain3(String explain) {this.explain3 = explain;}
    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {this.contents = contents;}
    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {this.pid = pid;}
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {this.createdAt = createdAt; }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {this.category = category;}
    public String getNickname(){ return nickname;}
    public void setNickname(String nickname){this.nickname = nickname;}
    public String getCallnumber(){ return callnumber;}
    public void setCallnumber(String callnumber){this.callnumber = callnumber;}
    public int getLikes(){ return likes; }
    public void setLikes(int likes){ this.likes = likes; }

}
