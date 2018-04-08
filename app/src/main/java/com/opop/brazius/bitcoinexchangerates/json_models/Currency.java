package com.opop.brazius.bitcoinexchangerates.json_models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currency implements Parcelable {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("rate_float")
    @Expose
    private Double rateFloat;

    /**
     * No args constructor for use in serialization
     *
     */
    public Currency() {
    }

    /**
     *
     * @param rateFloat
     * @param rate
     * @param symbol
     * @param description
     * @param code
     */
    public Currency(String code, String symbol, String rate, String description, Double rateFloat) {
        super();
        this.code = code;
        this.symbol = symbol;
        this.rate = rate;
        this.description = description;
        this.rateFloat = rateFloat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(Double rateFloat) {
        this.rateFloat = rateFloat;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.symbol);
        dest.writeString(this.rate);
        dest.writeString(this.description);
        dest.writeValue(this.rateFloat);
    }

    protected Currency(Parcel in) {
        this.code = in.readString();
        this.symbol = in.readString();
        this.rate = in.readString();
        this.description = in.readString();
        this.rateFloat = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<Currency> CREATOR = new Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel source) {
            return new Currency(source);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };
}
