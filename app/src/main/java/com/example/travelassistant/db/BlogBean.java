package com.example.travelassistant.db;

import java.io.Serializable;

public class BlogBean implements Serializable {
    private int _id;
    private String num;
    //标题
    private String name;
    private String avatar;
    private String nickname;
    private String introduce;
    private String content;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BlogBean() {
    }

    public BlogBean(int _id, String num, String name, String avatar, String nickname, String introduce, String content) {
        this._id = _id;
        this.num = num;
        this.name = name;
        this.avatar = avatar;
        this.nickname = nickname;
        this.introduce = introduce;
        this.content = content;
    }
}
