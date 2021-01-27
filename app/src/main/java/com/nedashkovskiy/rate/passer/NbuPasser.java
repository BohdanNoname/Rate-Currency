package com.nedashkovskiy.rate.passer;

import androidx.fragment.app.Fragment;

import com.nedashkovskiy.rate.model.ExchangeRate;

import java.util.List;

public interface NbuPasser {
    void setNbuFragment(Fragment nbuFragment);
    void setExchangeList(List<ExchangeRate> exchangeList);
}
