package com.projects.tutofire.fragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.tutofire.R;
import com.projects.tutofire.SharedViewModel;
import com.projects.tutofire.database.entity.Course;
import com.projects.tutofire.fragments.adapters.MyAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment {
    String TAG = "mTAG";
    LiveData<List<Course>> data;
    Context context;
    RecyclerView recyclerView;
    private SharedViewModel vm;
    private MyAdapter customAdapter;

    public CoursesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.recycler_courses);
        vm = ViewModelProviders.of(this).get(SharedViewModel.class);
        vm.init();
        vm.getData().observe(
                this, courses -> {
                    Log.d(TAG, "onChanged() called with: courses = [" + courses + "]");
                    customAdapter = new MyAdapter(getContext(), courses);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(customAdapter);
                }
        );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_courses, container, false);
    }

}
