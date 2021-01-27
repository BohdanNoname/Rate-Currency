package com.nedashkovskiy.rate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nedashkovskiy.rate.R;
import com.nedashkovskiy.rate.api_connection.ApiCallback;
import com.nedashkovskiy.rate.api_connection.Parser;
import com.nedashkovskiy.rate.passer.CurrencyPasser;
import com.nedashkovskiy.rate.passer.NbuPasser;
import com.nedashkovskiy.rate.fragment.NbuFragment;
import com.nedashkovskiy.rate.fragment.PBFragment;
import com.nedashkovskiy.rate.model.BaseData;
import com.nedashkovskiy.rate.model.ExchangeRate;
import com.nedashkovskiy.rate.passer.PositionPasser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Main extends AppCompatActivity implements NbuPasser, CurrencyPasser {

    public final static String API_URL = "https://api.privatbank.ua/p24api/exchange_rates?json&date=";
    public static final String NBU_FRAG_KEY = "NBU_FRAG_KEY";
    public static final String PB_FRAG_KEY = "PB_FRAG_KEY";

    private Fragment nbuFragment;
    private Fragment pbFragment;
    private List<ExchangeRate> exchangeRate;
    private PositionPasser positionPasser;

    public void setExchangeRate(List<ExchangeRate> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            nbuFragment = getSupportFragmentManager().getFragment(savedInstanceState, NBU_FRAG_KEY);
            pbFragment = getSupportFragmentManager().getFragment(savedInstanceState, PB_FRAG_KEY);
        } else {
            updateDataInFragments(getCurrentDate());
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, NBU_FRAG_KEY, nbuFragment);
        getSupportFragmentManager().putFragment(outState, PB_FRAG_KEY, pbFragment);
    }

    private void hideNavigationButton(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(uiOptions);
    }


    /*_________________________________________________________________________________*/
    public void updateDataInFragments(String date){
        Parser parser = new Parser();
        parser.getBaseData(API_URL + date, new ApiCallback() {
            @Override
            public void onSuccess(BaseData baseData) {
                    setFragment(baseData);
                    setExchangeRate(baseData.getExchangeRate());
            }
            @Override
            public void onFailure() {
                Toast.makeText(Main.this, "ConnectionFailed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFragment(BaseData baseData){
        nbuFragment = new NbuFragment(baseData, getCurrentDate());
        pbFragment = new PBFragment(baseData, getCurrentDate());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_private_bank, pbFragment).replace(R.id.fragment_nbu, nbuFragment);
        transaction.commit();
    }

    /*_________________________________________________________________________________*/

    public static String getCurrentDate(){
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return  dateFormat.format(currentDate);
    }

    public static String setFixedSize(String string, int length){
        char[] mas = new char[length];
        for (int i = 0; i < mas.length; i++){
            mas[i] = string.charAt(i);
        }
        return new String(mas);
    }

    /*_________________________________________________________________________________*/

    @Override
    public void markPosition(String massage) {
        RecyclerView recyclerView = nbuFragment.getView().findViewById(R.id.recycler_view_currency_nbu);
        positionPasser = (PositionPasser) recyclerView.getAdapter();
        int size = recyclerView.getAdapter().getItemCount();
        recyclerView.smoothScrollToPosition(10);

        for (int pos = 1; pos < size; pos++){
            if (exchangeRate.get(pos).getCurrency().equals(massage)){
                recyclerView.smoothScrollToPosition(pos-1);
                positionPasser.markCertainlyPosition(pos);
            }
        }
    }

    @Override
    public void setNbuFragment(Fragment nbuFragment) { this.nbuFragment = nbuFragment; }

    @Override
    public void setExchangeList(List<ExchangeRate> exchangeList) { this.exchangeRate = exchangeList; }
}