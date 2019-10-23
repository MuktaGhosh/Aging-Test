package com.example.babul.wsmt.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Profile implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String userName;

    @SerializedName("password")
    private String password;

    @SerializedName("userType_Id")
    private String userType_Id;

    @SerializedName("last_login")
    private String last_login;

    @SerializedName("active")
    private int active;

    @SerializedName("usertype")
    private UserType userType;

    private Profile(Parcel in) {
        id = in.readString();
        userName = in.readString();
        password = in.readString();
        userType_Id = in.readString();
        last_login = in.readString();
        active = in.readInt();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType_Id() {
        return userType_Id;
    }

    public void setUserType_Id(String userType_Id) {
        this.userType_Id = userType_Id;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(userType_Id);
        dest.writeString(last_login);
        dest.writeInt(active);
    }
}
