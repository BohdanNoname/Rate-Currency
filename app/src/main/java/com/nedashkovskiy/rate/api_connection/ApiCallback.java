package com.nedashkovskiy.rate.api_connection;

import com.nedashkovskiy.rate.model.BaseData;

public interface ApiCallback {
    void onSuccess(BaseData baseData);
    void onFailure();
}
