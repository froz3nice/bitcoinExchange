package com.opop.brazius.bitcoinexchangerates.json_models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BitcoinItem  implements Parcelable {

    @SerializedName("time")
    @Expose
    private Time time;
    @SerializedName("disclaimer")
    @Expose
    private String disclaimer;
    @SerializedName("chartName")
    @Expose
    private String chartName;
    @SerializedName("bpi")
    @Expose
    private Bpi bpi;

    /**
     * No args constructor for use in serialization
     *
     */
    public BitcoinItem() {
    }

    /**
     *
     * @param time
     * @param disclaimer
     * @param chartName
     * @param bpi
     */
    public BitcoinItem(Time time, String disclaimer, String chartName, Bpi bpi) {
        super();
        this.time = time;
        this.disclaimer = disclaimer;
        this.chartName = chartName;
        this.bpi = bpi;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public Bpi getBpi() {
        return bpi;
    }

    public void setBpi(Bpi bpi) {
        this.bpi = bpi;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.time, flags);
        dest.writeString(this.disclaimer);
        dest.writeString(this.chartName);
        dest.writeParcelable(this.bpi, flags);
    }

    protected BitcoinItem(Parcel in) {
        this.time = in.readParcelable(Time.class.getClassLoader());
        this.disclaimer = in.readString();
        this.chartName = in.readString();
        this.bpi = in.readParcelable(Bpi.class.getClassLoader());
    }

    public static final Creator<BitcoinItem> CREATOR = new Creator<BitcoinItem>() {
        @Override
        public BitcoinItem createFromParcel(Parcel source) {
            return new BitcoinItem(source);
        }

        @Override
        public BitcoinItem[] newArray(int size) {
            return new BitcoinItem[size];
        }
    };
}
