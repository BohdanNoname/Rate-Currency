package com.nedashkovskiy.rate.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.nedashkovskiy.rate.R;
import com.nedashkovskiy.rate.activity.Main;
import com.nedashkovskiy.rate.api_connection.ApiCallback;
import com.nedashkovskiy.rate.api_connection.Parser;
import com.nedashkovskiy.rate.model.BaseData;
import com.nedashkovskiy.rate.passer.NbuPasser;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private final FragmentActivity fragmentActivity;
    private final String TAG;
    private NbuPasser nbuPasser;

    public DatePickerFragment(FragmentActivity fragmentActivity, Activity activity, String tag) {
        this.nbuPasser = (NbuPasser) activity;
        this.fragmentActivity = fragmentActivity;
        this.TAG = tag;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(fragmentActivity, this, year, month, day);
    }

    /*_________________________________________________________________________________*/

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = setDate(day, month + 1, year);

        Parser parser = new Parser();
        parser.getBaseData(Main.API_URL + date, new ApiCallback() {
            @Override
            public void onSuccess(BaseData baseData) {
                int idLayout = 0;
                Fragment fragment = null;

                if (TAG.equals(NbuFragment.TAG_DATE_PICKER_NB)){
                    fragment = new NbuFragment(baseData, date);
                    idLayout = R.id.fragment_nbu;
                    nbuPasser.setNbuFragment(fragment);
                    nbuPasser.setExchangeList(baseData.getExchangeRate());
                }
                if (TAG.equals(PBFragment.TAG_DATE_PICKER_PB)){
                    fragment = new PBFragment(baseData, date);
                    idLayout = R.id.fragment_private_bank;
                }

                FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                assert fragment != null;
                transaction.replace(idLayout, fragment);
                transaction.commit();
            }

            @Override
            public void onFailure() {
                Toast.makeText(fragmentActivity, "Connection failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*_________________________________________________________________________________*/

    private String setDate(int day, int month, int year){
        if (day <= 9 && month <= 9 ){
            return "0" + day + "." + "0" + month  + "." + year;
        } else if (day <= 9){
            return "0" + day + "." + month  + "." + year;
        } else if (month <= 9){
            return day + "." + "0" + month + "." + year;
        } else {
            return day + "." + month + "." + year;
        }
    }
}
