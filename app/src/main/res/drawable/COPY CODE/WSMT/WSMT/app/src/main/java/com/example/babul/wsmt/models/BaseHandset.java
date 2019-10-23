package com.example.babul.wsmt.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class BaseHandset implements Parcelable {

    private List<Handset> handList = new ArrayList<>();

    private BaseHandset(Parcel in) {
        handList = in.createTypedArrayList(Handset.CREATOR);
    }

    public static final Creator<BaseHandset> CREATOR = new Creator<BaseHandset>() {
        @Override
        public BaseHandset createFromParcel(Parcel in) {
            return new BaseHandset(in);
        }

        @Override
        public BaseHandset[] newArray(int size) {
            return new BaseHandset[size];
        }
    };

    public List<Handset> getHandList() {
        return handList;
    }

    public void setHandList(List<Handset> handList) {
        this.handList = handList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(handList);
    }
}
