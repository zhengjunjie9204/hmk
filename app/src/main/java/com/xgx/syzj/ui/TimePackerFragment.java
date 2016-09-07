package com.xgx.syzj.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
@SuppressLint("ValidFragment")
public class TimePackerFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return   new DatePickerDialog(getActivity(),(SaleHistoryFilterActivity)getActivity() , year, month, day);
    }


}
