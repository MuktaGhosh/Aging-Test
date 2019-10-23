package com.example.babul.wsmt.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BomBase implements Parcelable {

    @SerializedName("total_items")
    @Expose
    private Integer totalItems;
    @SerializedName("total_reels")
    @Expose
    private String totalReels;

    @SerializedName("checked_reels")
    @Expose
    private String checked_reels;

    @SerializedName("items_details")
    @Expose
    private List<ItemsDetail> itemsDetails = null;

    private BomBase(Parcel in) {
        if (in.readByte() == 0) {
            totalItems = null;
        } else {
            totalItems = in.readInt();
        }
        totalReels = in.readString();
        checked_reels = in.readString();
        itemsDetails = in.createTypedArrayList(ItemsDetail.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (totalItems == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalItems);
        }
        dest.writeString(totalReels);
        dest.writeString(checked_reels);
        dest.writeTypedList(itemsDetails);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BomBase> CREATOR = new Creator<BomBase>() {
        @Override
        public BomBase createFromParcel(Parcel in) {
            return new BomBase(in);
        }

        @Override
        public BomBase[] newArray(int size) {
            return new BomBase[size];
        }
    };

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalReels() {
        return totalReels;
    }

    public void setTotalReels(String totalReels) {
        this.totalReels = totalReels;
    }

    public String getChecked_reels() {
        return checked_reels;
    }

    public void setChecked_reels(String checked_reels) {
        this.checked_reels = checked_reels;
    }

    public List<ItemsDetail> getItemsDetails() {
        return itemsDetails;
    }

    public void setItemsDetails(List<ItemsDetail> itemsDetails) {
        this.itemsDetails = itemsDetails;
    }
}

