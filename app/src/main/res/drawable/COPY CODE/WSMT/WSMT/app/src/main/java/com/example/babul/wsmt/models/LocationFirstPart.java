package com.example.babul.wsmt.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class LocationFirstPart implements Parcelable {

    private List<String> firstLocationList = new ArrayList<>();

    public LocationFirstPart() {

    }

    private LocationFirstPart(Parcel in) {
        firstLocationList = in.createStringArrayList();
    }

    public static final Creator<LocationFirstPart> CREATOR = new Creator<LocationFirstPart>() {
        @Override
        public LocationFirstPart createFromParcel(Parcel in) {
            return new LocationFirstPart(in);
        }

        @Override
        public LocationFirstPart[] newArray(int size) {
            return new LocationFirstPart[size];
        }
    };

    public List<String> getFirstLocationList() {
        return firstLocationList;
    }

    public void setFirstLocationList(List<String> firstLocationList) {
        this.firstLocationList = firstLocationList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(firstLocationList);
    }
}
