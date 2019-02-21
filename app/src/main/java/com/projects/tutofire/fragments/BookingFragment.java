package com.projects.tutofire.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.projects.tutofire.R;
import com.projects.tutofire.SharedViewModel;
import com.projects.tutofire.fragments.adapters.TeacherAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {
    String TAG = "mTAG_BookingFragment";

    RecyclerView rec_booking;
    EditText edt_booking;
    TextView txv_booking;
    SharedViewModel vm;
    TeacherAdapter customAdapter;


    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rec_booking = getView().findViewById(R.id.rec_booking);
        edt_booking = getView().findViewById(R.id.edt_booking);
        txv_booking = getView().findViewById(R.id.txv_booking);
        vm = ViewModelProviders.of(this).get(SharedViewModel.class);
        vm.init();
        vm.getTeachers().observe(
                this, teachers -> {
                    Log.d(TAG, "onChanged() called with: courses = [" + teachers + "]");
                    customAdapter = new TeacherAdapter(teachers);
                    rec_booking.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rec_booking.setAdapter(customAdapter);
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_booking, container, false);
    }
}
