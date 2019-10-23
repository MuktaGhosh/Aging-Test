package com.example.babul.wsmt.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feeder implements Parcelable{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("insertorder")
    @Expose
    private Integer insertorder;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("bom_id")
    @Expose
    private Integer bomId;
    @SerializedName("handset_id")
    @Expose
    private Integer handsetId;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("feedpitch")
    @Expose
    private Integer feedpitch;
    @SerializedName("QTY")
    @Expose
    private Integer qTY;
    @SerializedName("ref")
    @Expose
    private String ref;
    @SerializedName("alternatebom_id")
    @Expose
    private Integer alternatebomId;
    @SerializedName("partnumber")
    @Expose
    private String partnumber;
    @SerializedName("panel_number")
    @Expose
    private Integer panelNumber;
    @SerializedName("board_number")
    @Expose
    private Integer boardNumber;
    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

    protected Feeder(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            insertorder = null;
        } else {
            insertorder = in.readInt();
        }
        name = in.readString();
        type = in.readString();
        if (in.readByte() == 0) {
            bomId = null;
        } else {
            bomId = in.readInt();
        }
        if (in.readByte() == 0) {
            handsetId = null;
        } else {
            handsetId = in.readInt();
        }
        size = in.readString();
        if (in.readByte() == 0) {
            feedpitch = null;
        } else {
            feedpitch = in.readInt();
        }
        if (in.readByte() == 0) {
            qTY = null;
        } else {
            qTY = in.readInt();
        }
        ref = in.readString();
        if (in.readByte() == 0) {
            alternatebomId = null;
        } else {
            alternatebomId = in.readInt();
        }
        partnumber = in.readString();
        if (in.readByte() == 0) {
            panelNumber = null;
        } else {
            panelNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            boardNumber = null;
        } else {
            boardNumber = in.readInt();
        }
        location = in.readString();
        created = in.readString();
        modified = in.readString();
    }

    public static final Creator<Feeder> CREATOR = new Creator<Feeder>() {
        @Override
        public Feeder createFromParcel(Parcel in) {
            return new Feeder(in);
        }

        @Override
        public Feeder[] newArray(int size) {
            return new Feeder[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInsertorder() {
        return insertorder;
    }

    public void setInsertorder(Integer insertorder) {
        this.insertorder = insertorder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBomId() {
        return bomId;
    }

    public void setBomId(Integer bomId) {
        this.bomId = bomId;
    }

    public Integer getHandsetId() {
        return handsetId;
    }

    public void setHandsetId(Integer handsetId) {
        this.handsetId = handsetId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getFeedpitch() {
        return feedpitch;
    }

    public void setFeedpitch(Integer feedpitch) {
        this.feedpitch = feedpitch;
    }

    public Integer getqTY() {
        return qTY;
    }

    public void setqTY(Integer qTY) {
        this.qTY = qTY;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Integer getAlternatebomId() {
        return alternatebomId;
    }

    public void setAlternatebomId(Integer alternatebomId) {
        this.alternatebomId = alternatebomId;
    }

    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    public Integer getPanelNumber() {
        return panelNumber;
    }

    public void setPanelNumber(Integer panelNumber) {
        this.panelNumber = panelNumber;
    }

    public Integer getBoardNumber() {
        return boardNumber;
    }

    public void setBoardNumber(Integer boardNumber) {
        this.boardNumber = boardNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        if (insertorder == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(insertorder);
        }
        dest.writeString(name);
        dest.writeString(type);
        if (bomId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(bomId);
        }
        if (handsetId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(handsetId);
        }
        dest.writeString(size);
        if (feedpitch == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(feedpitch);
        }
        if (qTY == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(qTY);
        }
        dest.writeString(ref);
        if (alternatebomId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(alternatebomId);
        }
        dest.writeString(partnumber);
        if (panelNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(panelNumber);
        }
        if (boardNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(boardNumber);
        }
        dest.writeString(location);
        dest.writeString(created);
        dest.writeString(modified);
    }
}
