package com.nedashkovskiy.rate.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nedashkovskiy.rate.R;
import com.nedashkovskiy.rate.model.BaseData;
import com.nedashkovskiy.rate.model.ExchangeRate;
import com.nedashkovskiy.rate.recycler_view.AdapterCurrencyNbu;
import com.nedashkovskiy.rate.recycler_view.LinearLayoutRecyclerView;

import java.util.List;


public class NbuFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Activity activity;
    private BaseData baseData;
    private TextView dateTextView;
    private ImageView dateImageView;
    private String date;

    public final static String TAG_DATE_PICKER_NB = "NbuFragment";

    public NbuFragment(BaseData baseData, String date){
        this.baseData = baseData;
        this.date = date;
    }

    public List<ExchangeRate> getExchangeRatesList(){
        return baseData.getExchangeRate();
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nbu, container, false);
        setRetainInstance(true);
        initID(root);

        if (baseData != null){
            recyclerViewRealization(recyclerView, baseData.getExchangeRate());
        }
        return root;
    }

    /*_________________________________________________________________________________*/

    private void initID(View v){
        recyclerView = v.findViewById(R.id.recycler_view_currency_nbu);
        dateTextView = v.findViewById(R.id.date);
        dateImageView = v.findViewById(R.id.date_image_view);

        dateTextView.setText(date);

        dateTextView.setOnClickListener(this);
        dateImageView.setOnClickListener(this);
    }

    private void recyclerViewRealization(RecyclerView view, List<ExchangeRate> list){
        view.setLayoutManager(new LinearLayoutRecyclerView(getContext()));
        view.setAdapter(new AdapterCurrencyNbu(list));
    }

    /*_________________________________________________________________________________*/

    @Override
    public void onClick(View v) {
        DialogFragment fragment = new DatePickerFragment((FragmentActivity) activity, activity, TAG_DATE_PICKER_NB);
        fragment.show(((FragmentActivity) activity).getSupportFragmentManager(), "date picker");
    }

    /*_________________________________________________________________________________*/
}