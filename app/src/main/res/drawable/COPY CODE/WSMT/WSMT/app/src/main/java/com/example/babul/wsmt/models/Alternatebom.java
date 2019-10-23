package com.example.babul.wsmt.models;

import android.app.AlertDialog;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Alternatebom implements Parcelable {

    @SerializedName("partnumber")
    @Expose
    private String partnumber;

    @SerializedName("manufacturer_partnumber")
    @Expose
    private String manufacturerPartnumber;
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("feeders")
    @Expose
    private List<Feeder> feeders = null;

    public Alternatebom(){

    }

    private Alternatebom(Parcel in) {
        partnumber = in.readString();
        manufacturerPartnumber = in.readString();
        description = in.readString();
        feeders = in.createTypedArrayList(Feeder.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(partnumber);
        dest.writeString(manufacturerPartnumber);
        dest.writeString(description);
        dest.writeTypedList(feeders);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Alternatebom> CREATOR = new Creator<Alternatebom>() {
        @Override
        public Alternatebom createFromParcel(Parcel in) {
            return new Alternatebom(in);
        }

        @Override
        public Alternatebom[] newArray(int size) {
            return new Alternatebom[size];
        }
    };

    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    public String getManufacturerPartnumber() {
        return manufacturerPartnumber;
    }

    public void setManufacturerPartnumber(String manufacturerPartnumber) {
        this.manufacturerPartnumber = manufacturerPartnumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Feeder> getFeeders() {
        return feeders;
    }

    public void setFeeders(List<Feeder> feeders) {
        this.feeders = feeders;
    }
}
