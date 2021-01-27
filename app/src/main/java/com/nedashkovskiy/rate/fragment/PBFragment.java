package com.nedashkovskiy.rate.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.nedashkovskiy.rate.R;
import com.nedashkovskiy.rate.activity.Main;
import com.nedashkovskiy.rate.model.BaseData;
import com.nedashkovskiy.rate.model.ExchangeRate;
import com.nedashkovskiy.rate.passer.CurrencyPasser;

import java.util.List;
import java.util.Objects;


public class PBFragment extends Fragment {
    private TextView saleEUR,  saleUSD,  saleRUB, purchaseEUR, purchaseUSD, purchaseRUB, dateTextView;
    private TableRow lineEUR, lineUSD, lineRUB;
    private ImageView dateImageView;
    private Activity activity;
    private CurrencyPasser currencyPasser;
    private BaseData baseData;
    private String date;

    public final static String TAG_DATE_PICKER_PB = "PBFragment";

    public PBFragment(BaseData baseData, String date){
        this.baseData = baseData;
        this.date = date;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        this.currencyPasser = (CurrencyPasser) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_private_bank, container,false);
        setRetainInstance(true);
        initID(root);

        if (baseData != null){
            setDataToPriceCurrency(baseData.getExchangeRate());
        }
        return root;
    }

    private void initID(View v){
        saleEUR = v.findViewById(R.id.sale_eur);
        saleUSD= v.findViewById(R.id.sale_usd);
        saleRUB= v.findViewById(R.id.sale_rub);

        purchaseEUR= v.findViewById(R.id.purchase_eur);
        purchaseUSD = v.findViewById(R.id.purchase_usd);
        purchaseRUB = v.findViewById(R.id.purchase_rub);

        dateTextView = v.findViewById(R.id.date);
        dateImageView = v.findViewById(R.id.date_image_view);
        dateTextView.setOnClickListener(showDatePickerDialog());
        dateImageView.setOnClickListener(showDatePickerDialog());

        lineEUR = v.findViewById(R.id.line_eur);
        lineUSD = v.findViewById(R.id.line_usd);
        lineRUB = v.findViewById(R.id.line_rub);

        lineEUR.setOnClickListener(scrollRecyclerviewInNbu("EUR"));
        lineUSD.setOnClickListener(scrollRecyclerviewInNbu("USD"));
        lineRUB.setOnClickListener(scrollRecyclerviewInNbu("RUB"));
    }

    /*_________________________________________________________________________________*/

    private View.OnClickListener scrollRecyclerviewInNbu(String currency){
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.click_animation);
                v.startAnimation(animation);
                currencyPasser.markPosition(currency);
            }
        };
        return clickListener;
    }

    private View.OnClickListener showDatePickerDialog(){
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new DatePickerFragment((FragmentActivity) activity, activity, TAG_DATE_PICKER_PB);
                fragment.show(((FragmentActivity) activity).getSupportFragmentManager(), "date picker");
            }
        };
        return clickListener;
    }

/*_________________________________________________________________________________*/

    private boolean setDataToPriceCurrency(List<ExchangeRate> exchangeRatesList){
        dateTextView.setText(date);

        for (int value = 0; value < exchangeRatesList.size(); value++) {
            if (Objects.equals(exchangeRatesList.get(value).getCurrency(), "EUR")){

                saleEUR.setText(Main.setFixedSize(exchangeRatesList.get(value).getSaleRate(), 6));
                purchaseEUR.setText(Main.setFixedSize(exchangeRatesList.get(value).getPurchaseRate(), 6));
            } else if (Objects.equals(exchangeRatesList.get(value).getCurrency(), "RUB")){

                saleRUB.setText(Main.setFixedSize(exchangeRatesList.get(value).getSaleRate(), 6));
                purchaseRUB.setText(Main.setFixedSize(exchangeRatesList.get(value).getPurchaseRate(), 6));
            }  else if (Objects.equals(exchangeRatesList.get(value).getCurrency(), "USD")){

                saleUSD.setText(Main.setFixedSize(exchangeRatesList.get(value).getSaleRate(), 6));
                purchaseUSD.setText(Main.setFixedSize(exchangeRatesList.get(value).getPurchaseRate(), 6));
            }
        }
        return true;
    }
}