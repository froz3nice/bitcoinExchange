package com.opop.brazius.bitcoinexchangerates.json_models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bpi implements Parcelable {

    @SerializedName("USD")
    @Expose
    private Currency usd;
    @SerializedName("GBP")
    @Expose
    private Currency gbp;
    @SerializedName("EUR")
    @Expose
    private Currency eur;

    /**
     * No args constructor for use in serialization
     */
    public Bpi() {
    }

    /**
     * @param usd
     * @param gbp
     * @param eur
     */
    public Bpi(Currency usd, Currency gbp, Currency eur) {
        super();
        this.usd = usd;
        this.gbp = gbp;
        this.eur = eur;
    }

    public Currency getUSD() {
        return usd;
    }

    public void setUSD(Currency uSD) {
        this.usd = uSD;
    }

    public Currency getGBP() {
        return gbp;
    }

    public void setGBP(Currency gBP) {
        this.gbp = gBP;
    }

    public Currency getEUR() {
        return eur;
    }

    public void setEUR(Currency eUR) {
        this.eur = eUR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.usd, flags);
        dest.writeParcelable(this.gbp, flags);
        dest.writeParcelable(this.eur, flags);
    }

    protected Bpi(Parcel in) {
        this.usd = in.readParcelable(Currency.class.getClassLoader());
        this.gbp = in.readParcelable(Currency.class.getClassLoader());
        this.eur = in.readParcelable(Currency.class.getClassLoader());
    }

    public static final Creator<Bpi> CREATOR = new Creator<Bpi>() {
        @Override
        public Bpi createFromParcel(Parcel source) {
            return new Bpi(source);
        }

        @Override
        public Bpi[] newArray(int size) {
            return new Bpi[size];
        }
    };
}