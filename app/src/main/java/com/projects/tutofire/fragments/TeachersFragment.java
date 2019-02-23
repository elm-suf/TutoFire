package com.projects.tutofire.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.tutofire.R;
import com.projects.tutofire.SharedViewModel;
import com.projects.tutofire.database.entity.Teacher;
import com.projects.tutofire.fragments.adapters.TeacherAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeachersFragment extends Fragment implements TeacherAdapter.OnTeacherListener {
    String TAG = "mTAG_BookingFragment";

    RecyclerView rec_booking;
    EditText edt_booking;
    TextView txv_booking;
    SharedViewModel vm;
    TeacherAdapter customAdapter;


    public TeachersFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        vm = ViewModelProviders.of(this).get(SharedViewModel.class);
        super.onActivityCreated(savedInstanceState);
        rec_booking = getView().findViewById(R.id.rec_booking);
        edt_booking = getView().findViewById(R.id.edt_booking);
        txv_booking = getView().findViewById(R.id.txv_booking);


        if (getArguments() == null) {
            vm.getTeachers().observe(
                    this, teachers -> {
                        Log.d(TAG, "onChanged() called with: courses = [" + teachers + "]");
                        customAdapter = new TeacherAdapter(teachers, this);
                        rec_booking.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rec_booking.setAdapter(customAdapter);
                    }
            );
        } else {
            txv_booking.setText((CharSequence) getArguments().get("id"));
            HashMap<String, String> map = (HashMap) getArguments().get("teachers");
            List<Teacher> teachers = new ArrayList<>();
            if (map == null) {
                Toast.makeText(getContext(), "No teachers for this course", LENGTH_SHORT).show();
            } else {
                map.forEach((k, v) -> teachers.add(new Teacher(k, v)));
            }
            customAdapter = new TeacherAdapter(teachers, this);
            rec_booking.setLayoutManager(new LinearLayoutManager(getActivity()));
            rec_booking.setAdapter(customAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teachers, container, false);
    }

    @Override
    public void onItemClicked(Teacher teacher) {
        Toast.makeText(getContext(), "item click " + teacher, Toast.LENGTH_SHORT).show();
    }
}
