package com.opop.brazius.bitcoinexchangerates.json_models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Time implements Parcelable {

    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("updatedISO")
    @Expose
    private String updatedISO;
    @SerializedName("updateduk")
    @Expose
    private String updateduk;

    /**
     * No args constructor for use in serialization
     *
     */
    public Time() {
    }

    /**
     *
     * @param updatedISO
     * @param updated
     * @param updateduk
     */
    public Time(String updated, String updatedISO, String updateduk) {
        super();
        this.updated = updated;
        this.updatedISO = updatedISO;
        this.updateduk = updateduk;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdatedISO() {
        return updatedISO;
    }

    public void setUpdatedISO(String updatedISO) {
        this.updatedISO = updatedISO;
    }

    public String getUpdateduk() {
        return updateduk;
    }

    public void setUpdateduk(String updateduk) {
        this.updateduk = updateduk;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.updated);
        dest.writeString(this.updatedISO);
        dest.writeString(this.updateduk);
    }

    protected Time(Parcel in) {
        this.updated = in.readString();
        this.updatedISO = in.readString();
        this.updateduk = in.readString();
    }

    public static final Creator<Time> CREATOR = new Creator<Time>() {
        @Override
        public Time createFromParcel(Parcel source) {
            return new Time(source);
        }

        @Override
        public Time[] newArray(int size) {
            return new Time[size];
        }
    };
}