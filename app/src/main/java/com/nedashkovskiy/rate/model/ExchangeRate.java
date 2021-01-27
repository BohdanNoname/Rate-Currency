package com.nedashkovskiy.rate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ExchangeRate {

    @SerializedName("baseCurrency")
    @Expose
    private String baseCurrencyAbbreviation;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("saleRateNB")
    @Expose
    private String saleRateNB;

    @SerializedName("purchaseRateNB")
    @Expose
    private String purchaseRateNB;

    @SerializedName("saleRate")
    @Expose
    private String saleRate;

    @SerializedName("purchaseRate")
    @Expose
    private String purchaseRate;

    public ExchangeRate() {
    }

    public ExchangeRate(String baseCurrencyAbbreviation, String currency, String saleRateNB, String purchaseRateNB, String saleRate, String purchaseRate) {
        this.baseCurrencyAbbreviation = baseCurrencyAbbreviation;
        this.currency = currency;
        this.saleRateNB = saleRateNB;
        this.purchaseRateNB = purchaseRateNB;
        this.saleRate = saleRate;
        this.purchaseRate = purchaseRate;
    }

    public String getBaseCurrencyAbbreviation() {
        return baseCurrencyAbbreviation;
    }

    public String getCurrency() {
        return currency;
    }


    public String getSaleRateNB() {
        return saleRateNB;
    }

    public String getPurchaseRateNB() {
        return purchaseRateNB;
    }

    public String getSaleRate() {
        return saleRate;
    }

    public String getPurchaseRate() {
        return purchaseRate;
    }
}
