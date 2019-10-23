package com.example.babul.wsmt.models;

import com.google.gson.annotations.SerializedName;

public class UserType {
    @SerializedName("id")
    private int userid;

    @SerializedName("title")
    private String title;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
