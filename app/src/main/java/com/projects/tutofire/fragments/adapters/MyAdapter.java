package com.projects.tutofire.fragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.tutofire.R;
import com.projects.tutofire.activities.BookingActivity;
import com.projects.tutofire.database.entity.Course;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public static String EXTRA_COURSE = "EXTRA_COURSE";
    String TAG = "mTAG";

    List<Course> listOfCourses;
    private LayoutInflater inflater;
    private Context context;

    public MyAdapter(Context context, List<Course> listOfCourses) {
        this.listOfCourses = listOfCourses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.course_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Course course = listOfCourses.get(i);
        viewHolder.txv_title.setText(course.getTitle());
        viewHolder.txv_description.setText(course.getDescription());
        viewHolder.card_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BookingActivity.class);
                intent.putExtra(EXTRA_COURSE, course.getTitle());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txv_title;
        public TextView txv_description;
        public CardView card_course;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txv_title = itemView.findViewById(R.id.txv_title);
            txv_description = itemView.findViewById(R.id.txv_description);
            card_course = itemView.findViewById(R.id.card_course);
        }

    }
}
