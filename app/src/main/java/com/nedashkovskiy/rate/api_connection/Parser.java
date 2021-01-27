package com.nedashkovskiy.rate.api_connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nedashkovskiy.rate.model.BaseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Parser {

    public void getBaseData(String url, ApiCallback apiCallback){
        ApiClient client = retrofitConnection().create(ApiClient.class);
        Call<BaseData> call = client.getCurrency(url);
        call.enqueue(new Callback<BaseData>() {
            @Override
            public void onResponse(Call<BaseData> call, Response<BaseData> response) {
                if (response.isSuccessful()){
                    apiCallback.onSuccess(new BaseData(response.body().getDate(), response.body().getBank(),
                            response.body().getBaseCurrency(), response.body().getBaseCurrencyLit(), response.body().getExchangeRate()));
                }
            }

            @Override
            public void onFailure(Call<BaseData> call, Throwable t) {
                apiCallback.onFailure();
            }
        });
    }

    private Retrofit retrofitConnection(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        String API_URL = "https://api.privatbank.ua/";
        return new Retrofit.Builder().
                baseUrl(API_URL).
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
    }

}
