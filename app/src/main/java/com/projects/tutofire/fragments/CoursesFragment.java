package com.projects.tutofire.fragments;


import android.arch.lifecycle.LiveData;
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

import com.projects.tutofire.R;
import com.projects.tutofire.SharedViewModel;
import com.projects.tutofire.database.entity.Course;
import com.projects.tutofire.fragments.adapters.CourseAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment implements CourseAdapter.OnCourseListener {
    String TAG = "mTAG";
    LiveData<List<Course>> data;
    RecyclerView recyclerView;
    private CourseAdapter customAdapter;

    public CoursesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.recycler_courses);
        SharedViewModel vm = ViewModelProviders.of(this).get(SharedViewModel.class);
        vm.getDataCourses().observe(
                this, courses -> {
                    Log.d(TAG, "onChanged() called with: courses = [" + courses + "]");
                    customAdapter = new CourseAdapter(courses, this);
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

    @Override
    public void onItemClicked(Course course) {
        Log.d(TAG, "onItemClicked() called with: course = [" + course + "]");
        Fragment fragment = new TeachersFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", course.getTitle());
        bundle.putString("id", course.getId());
        bundle.putString("description", course.getDescription());
        bundle.putSerializable("teachers", (Serializable) course.getTeachers());
        fragment.setArguments(bundle);
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().replace(R.id.container_home, fragment).addToBackStack(null).commit();
    }
}
