package com.example.babul.wsmt.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Handset implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("bompattern")
    @Expose
    private String bompattern;

    private Handset(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        bompattern = in.readString();
    }

    public static final Creator<Handset> CREATOR = new Creator<Handset>() {
        @Override
        public Handset createFromParcel(Parcel in) {
            return new Handset(in);
        }

        @Override
        public Handset[] newArray(int size) {
            return new Handset[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBompattern() {
        return bompattern;
    }

    public void setBompattern(String bompattern) {
        this.bompattern = bompattern;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(title);
        dest.writeString(bompattern);
    }
}
