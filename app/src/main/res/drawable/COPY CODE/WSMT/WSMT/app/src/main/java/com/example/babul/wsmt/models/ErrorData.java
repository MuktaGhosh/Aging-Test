package com.example.babul.wsmt.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ErrorData implements Parcelable{
    @SerializedName("return")
    private boolean errorREturn;

    @SerializedName("data")

    private String errorData;

    private ErrorData(Parcel in) {
        errorREturn = in.readByte() != 0;
        errorData = in.readString();
    }

    public static final Creator<ErrorData> CREATOR = new Creator<ErrorData>() {
        @Override
        public ErrorData createFromParcel(Parcel in) {
            return new ErrorData(in);
        }

        @Override
        public ErrorData[] newArray(int size) {
            return new ErrorData[size];
        }
    };

    public boolean isErrorREturn() {
        return errorREturn;
    }

    public void setErrorREturn(boolean errorREturn) {
        this.errorREturn = errorREturn;
    }

    public String getErrorData() {
        return errorData;
    }

    public void setErrorData(String errorData) {
        this.errorData = errorData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (errorREturn ? 1 : 0));
        dest.writeString(errorData);
    }
}
