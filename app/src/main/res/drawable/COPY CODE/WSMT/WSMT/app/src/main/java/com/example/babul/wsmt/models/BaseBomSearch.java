package com.example.babul.wsmt.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class BaseBomSearch implements Parcelable {

    private List<SearchBom> handList = new ArrayList<>();

    private BaseBomSearch(Parcel in) {
        handList = in.createTypedArrayList(SearchBom.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(handList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseBomSearch> CREATOR = new Creator<BaseBomSearch>() {
        @Override
        public BaseBomSearch createFromParcel(Parcel in) {
            return new BaseBomSearch(in);
        }

        @Override
        public BaseBomSearch[] newArray(int size) {
            return new BaseBomSearch[size];
        }
    };

    public List<SearchBom> getHandList() {
        return handList;
    }

    public void setHandList(List<SearchBom> handList) {
        this.handList = handList;
    }
}
