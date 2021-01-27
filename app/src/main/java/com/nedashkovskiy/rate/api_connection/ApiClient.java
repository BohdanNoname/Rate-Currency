package com.nedashkovskiy.rate.api_connection;

import com.nedashkovskiy.rate.model.BaseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiClient {
    @GET
    Call<BaseData> getCurrency(@Url String url);
}
