package com.projects.tutofire.fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.tutofire.R;
import com.projects.tutofire.SharedViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookingFragment extends Fragment {

    final Calendar myCalendar = Calendar.getInstance();
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    EditText edt_date_pick;
    TextView txv_booking;
    RadioGroup radioGroup;
    SharedViewModel vm;


    public BookingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = ViewModelProviders.of(this).get(SharedViewModel.class);

        radioGroup = getView().findViewById(R.id.radioGroup);
        txv_booking = getView().findViewById(R.id.txv_booking);
        edt_date_pick = getView().findViewById(R.id.edt_date_pick);
        btn_1 = getView().findViewById(R.id.btn_slot_1);
        btn_2 = getView().findViewById(R.id.btn_slot_2);
        btn_3 = getView().findViewById(R.id.btn_slot_3);
        btn_4 = getView().findViewById(R.id.btn_slot_4);

        btn_1.setOnClickListener(v -> bookLesson(btn_1, 1));
        btn_2.setOnClickListener(v -> bookLesson(btn_2, 2));
        btn_3.setOnClickListener(v -> bookLesson(btn_3, 3));
        btn_4.setOnClickListener(v -> bookLesson(btn_4, 4));


        //date_string = DateFormat.format("yyyy-MM-dd", new Date()).toString();
        Date dd = new Date();
        edt_date_pick.setText(dd.toLocaleString());


        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        edt_date_pick.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            new DatePickerDialog(
                    v.getContext(), date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
                    .show();
        });
    }

    @SuppressLint("ResourceAsColor")
    private void bookLesson(Button btn, int time_slot) {
        Toast.makeText(getContext(), "click time-slot " + time_slot, Toast.LENGTH_SHORT).show();
        btn.setBackgroundColor(Color.parseColor("#af4646"));
        btn.setTextColor(Color.parseColor("#d2b81e"));
        btn.setClickable(false);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edt_date_pick.setText(sdf.format(myCalendar.getTime()));
    }

}
