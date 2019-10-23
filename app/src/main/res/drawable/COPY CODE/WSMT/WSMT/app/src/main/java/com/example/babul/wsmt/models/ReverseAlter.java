package com.example.babul.wsmt.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReverseAlter implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("partnumber")
    @Expose
    private String partnumber;
    @SerializedName("bom_id")
    @Expose
    private Integer bomId;
    @SerializedName("manufacturer_partnumber")
    @Expose
    private String manufacturerPartnumber;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("handset_id")
    @Expose
    private Integer handsetId;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("bom")
    @Expose
    private ReverseBOM bom;


    public ReverseAlter(){

    }
    private ReverseAlter(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        partnumber = in.readString();
        if (in.readByte() == 0) {
            bomId = null;
        } else {
            bomId = in.readInt();
        }
        manufacturerPartnumber = in.readString();
        description = in.readString();
        manufacturer = in.readString();
        value = in.readString();
        if (in.readByte() == 0) {
            handsetId = null;
        } else {
            handsetId = in.readInt();
        }
        created = in.readString();
        modified = in.readString();
        bom = in.readParcelable(ReverseBOM.class.getClassLoader());
    }

    public static final Creator<ReverseAlter> CREATOR = new Creator<ReverseAlter>() {
        @Override
        public ReverseAlter createFromParcel(Parcel in) {
            return new ReverseAlter(in);
        }

        @Override
        public ReverseAlter[] newArray(int size) {
            return new ReverseAlter[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    public Integer getBomId() {
        return bomId;
    }

    public void setBomId(Integer bomId) {
        this.bomId = bomId;
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getHandsetId() {
        return handsetId;
    }

    public void setHandsetId(Integer handsetId) {
        this.handsetId = handsetId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public ReverseBOM getBom() {
        return bom;
    }

    public void setBom(ReverseBOM bom) {
        this.bom = bom;
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
        dest.writeString(partnumber);
        if (bomId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(bomId);
        }
        dest.writeString(manufacturerPartnumber);
        dest.writeString(description);
        dest.writeString(manufacturer);
        dest.writeString(value);
        if (handsetId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(handsetId);
        }
        dest.writeString(created);
        dest.writeString(modified);
        dest.writeParcelable(bom, flags);
    }
}
