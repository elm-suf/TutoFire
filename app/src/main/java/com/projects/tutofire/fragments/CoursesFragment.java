package com.projects.tutofire.fragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.projects.tutofire.activities.BookingActivity;
import com.projects.tutofire.database.entity.Course;
import com.projects.tutofire.fragments.adapters.CourseAdapter;

import java.util.List;

import static com.projects.tutofire.fragments.adapters.CourseAdapter.EXTRA_COURSE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment implements CourseAdapter.OnCourseListener {
    String TAG = "mTAG";
    LiveData<List<Course>> data;
    RecyclerView recyclerView;
    private SharedViewModel vm;
    private CourseAdapter customAdapter;

    public CoursesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.recycler_courses);
        vm = ViewModelProviders.of(this).get(SharedViewModel.class);
        vm.init();
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
        Intent intent = new Intent(getContext(), BookingActivity.class);
        intent.putExtra(EXTRA_COURSE, course.getTitle());
        Fragment fragment = new BookingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", course.getTitle());
        bundle.putString("id", course.getId());
        bundle.putString("description", course.getDescription());
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.container_home, fragment).addToBackStack(null).commit();
    }
}
