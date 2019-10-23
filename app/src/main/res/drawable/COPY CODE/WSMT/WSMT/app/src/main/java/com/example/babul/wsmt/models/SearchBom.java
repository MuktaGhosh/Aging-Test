package com.example.babul.wsmt.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchBom implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("partnumber")
    @Expose
    private String partnumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_cn")
    @Expose
    private String nameCn;
    @SerializedName("manufacturer_partnumber")
    @Expose
    private String manufacturerPartnumber;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("QTY")
    @Expose
    private Integer qTY;
    @SerializedName("total_qty")
    @Expose
    private Integer totalQty;
    @SerializedName("number_of_reel")
    @Expose
    private Integer numberOfReel;
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


    @SerializedName("feeders")
    @Expose
    private List<Feeder> feeders = null;

    @SerializedName("alternate_info")
    @Expose
    private Alternatebom alternateboms = null;

    private SearchBom(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        partnumber = in.readString();
        name = in.readString();
        nameCn = in.readString();
        manufacturerPartnumber = in.readString();
        description = in.readString();
        reference = in.readString();
        if (in.readByte() == 0) {
            qTY = null;
        } else {
            qTY = in.readInt();
        }
        if (in.readByte() == 0) {
            totalQty = null;
        } else {
            totalQty = in.readInt();
        }
        if (in.readByte() == 0) {
            numberOfReel = null;
        } else {
            numberOfReel = in.readInt();
        }
        manufacturer = in.readString();
        value = in.readString();
        if (in.readByte() == 0) {
            handsetId = null;
        } else {
            handsetId = in.readInt();
        }
        created = in.readString();
        modified = in.readString();
        feeders = in.createTypedArrayList(Feeder.CREATOR);
        alternateboms = in.readParcelable(Alternatebom.class.getClassLoader());
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
        dest.writeString(name);
        dest.writeString(nameCn);
        dest.writeString(manufacturerPartnumber);
        dest.writeString(description);
        dest.writeString(reference);
        if (qTY == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(qTY);
        }
        if (totalQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalQty);
        }
        if (numberOfReel == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberOfReel);
        }
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
        dest.writeTypedList(feeders);
        dest.writeParcelable(alternateboms, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchBom> CREATOR = new Creator<SearchBom>() {
        @Override
        public SearchBom createFromParcel(Parcel in) {
            return new SearchBom(in);
        }

        @Override
        public SearchBom[] newArray(int size) {
            return new SearchBom[size];
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getqTY() {
        return qTY;
    }

    public void setqTY(Integer qTY) {
        this.qTY = qTY;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getNumberOfReel() {
        return numberOfReel;
    }

    public void setNumberOfReel(Integer numberOfReel) {
        this.numberOfReel = numberOfReel;
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

    public List<Feeder> getFeeders() {
        return feeders;
    }

    public void setFeeders(List<Feeder> feeders) {
        this.feeders = feeders;
    }

    public Alternatebom getAlternateboms() {
        return alternateboms;
    }

    public void setAlternateboms(Alternatebom alternateboms) {
        this.alternateboms = alternateboms;
    }
}

