package com.example.babul.wsmt.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemsDetail implements Parcelable {
    @SerializedName("number_of_reels")
    @Expose
    private String numberOfReels;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("reel_size")
    @Expose
    private String reelSize;
    @SerializedName("reels_scaned")
    @Expose
    private String reels_scaned;

    private ItemsDetail(Parcel in) {
        numberOfReels = in.readString();
        itemName = in.readString();
        reelSize = in.readString();
        reels_scaned = in.readString();
    }

    public static final Creator<ItemsDetail> CREATOR = new Creator<ItemsDetail>() {
        @Override
        public ItemsDetail createFromParcel(Parcel in) {
            return new ItemsDetail(in);
        }

        @Override
        public ItemsDetail[] newArray(int size) {
            return new ItemsDetail[size];
        }
    };

    public String getNumberOfReels() {
        return numberOfReels;
    }

    public void setNumberOfReels(String numberOfReels) {
        this.numberOfReels = numberOfReels;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getReelSize() {
        return reelSize;
    }

    public void setReelSize(String reelSize) {
        this.reelSize = reelSize;
    }

    public String getReels_scaned() {
        return reels_scaned;
    }

    public void setReels_scaned(String reels_scaned) {
        this.reels_scaned = reels_scaned;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(numberOfReels);
        dest.writeString(itemName);
        dest.writeString(reelSize);
        dest.writeString(reels_scaned);
    }
}

