package com.nedashkovskiy.rate.recycler_view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nedashkovskiy.rate.R;
import com.nedashkovskiy.rate.activity.Main;
import com.nedashkovskiy.rate.model.ExchangeRate;
import com.nedashkovskiy.rate.passer.PositionPasser;

import java.util.List;

public class AdapterCurrencyNbu extends RecyclerView.Adapter<AdapterCurrencyNbu.ViewHolderCurrencyNbu> implements PositionPasser {
    private List<ExchangeRate> exchangeRatesList;
    private ViewHolderCurrencyNbu viewHolderCurrencyNbu;

    public AdapterCurrencyNbu(List<ExchangeRate> list) {
        this.exchangeRatesList = list;
    }

    @NonNull
    @Override
    public ViewHolderCurrencyNbu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_nbu, parent, false);
        viewHolderCurrencyNbu = new ViewHolderCurrencyNbu(view, exchangeRatesList);
        viewHolderCurrencyNbu.linearLayout.findViewById(R.id.linear_layout_recycler_view);
        return viewHolderCurrencyNbu;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderCurrencyNbu holder, int position) {
        String currencyNB = exchangeRatesList.get(position + 1).getCurrency();
        String purchaseRateNB = exchangeRatesList.get(position + 1).getPurchaseRateNB();

        if (position <= exchangeRatesList.size()){
            int price = 1;
            holder.headerCurrency.setText(currencyNB);

            if (Float.parseFloat(purchaseRateNB) < 1.0){
                float purchase = Float.parseFloat(purchaseRateNB) * 10;

                holder.priceUAH.setText(Main.setFixedSize(String.valueOf(purchase), 5) + "UAH");
                holder.currency.setText(10 + currencyNB);
            } else {
                holder.priceUAH.setText(Main.setFixedSize(purchaseRateNB, 5) + "UAH");
                holder.currency.setText(price + currencyNB);
            }
        }
    }

    @Override
    public int getItemCount() {
        return exchangeRatesList.size() - 1;
    }

    @Override
    public void markCertainlyPosition(int position) {

    }

    /*________________ViewHolderCurrencyNbu_________________*/

    public static class ViewHolderCurrencyNbu extends RecyclerView.ViewHolder {
        TextView headerCurrency, priceUAH, currency;
        LinearLayout linearLayout;

        public ViewHolderCurrencyNbu(@NonNull View itemView, List<ExchangeRate> list) {
            super(itemView);
            headerCurrency = itemView.findViewById(R.id.name_currency);
                priceUAH = itemView.findViewById(R.id.price_UAH);
            currency = itemView.findViewById(R.id.currency);
            linearLayout = itemView.findViewById(R.id.linear_layout_recycler_view);
        }
    }
}
