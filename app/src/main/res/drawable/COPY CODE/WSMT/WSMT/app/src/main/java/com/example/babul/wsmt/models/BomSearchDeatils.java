package com.example.babul.wsmt.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BomSearchDeatils {
    @SerializedName("reels_checked")
    @Expose
    private String reelsChecked;
    @SerializedName("partnumber")
    @Expose
    private String partnumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_cn")
    @Expose
    private String nameCn;
    @SerializedName("total_reel")
    @Expose
    private String totalReel;

    public String getReelsChecked() {
        return reelsChecked;
    }

    public void setReelsChecked(String reelsChecked) {
        this.reelsChecked = reelsChecked;
    }

    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getTotalReel() {
        return totalReel;
    }

    public void setTotalReel(String totalReel) {
        this.totalReel = totalReel;
    }
}
