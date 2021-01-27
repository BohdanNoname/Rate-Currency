package com.nedashkovskiy.rate.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BaseData {
    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("bank")
    @Expose
    private String bank;

    @SerializedName("baseCurrency")
    @Expose
    private String baseCurrency;

    @SerializedName("baseCurrencyLit")
    @Expose
    private String baseCurrencyLit;

    @SerializedName("exchangeRate")
    @Expose
    private List<ExchangeRate> exchangeRate;

    public BaseData(String date, String bank, String baseCurrency, String baseCurrencyLit, List<ExchangeRate> exchangeRate) {
        this.date = date;
        this.bank = bank;
        this.baseCurrency = baseCurrency;
        this.baseCurrencyLit = baseCurrencyLit;
        this.exchangeRate = exchangeRate;
    }

    public String getDate() {
        return date;
    }

    public String getBank() {
        return bank;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getBaseCurrencyLit() {
        return baseCurrencyLit;
    }

    public List<ExchangeRate> getExchangeRate() {
        return exchangeRate;
    }

}