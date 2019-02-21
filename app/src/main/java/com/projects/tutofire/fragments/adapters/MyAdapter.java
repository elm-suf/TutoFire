package com.projects.tutofire.fragments.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.tutofire.R;
import com.projects.tutofire.database.entity.Course;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public static String EXTRA_COURSE = "EXTRA_COURSE";
    String TAG = "mTAG";

    private List<Course> listOfCourses;
    private LayoutInflater inflater;
    private OnCourseListener onCourseListener;


    public MyAdapter(List<Course> listOfCourses, OnCourseListener onCourseListener) {
        this.listOfCourses = listOfCourses;
        this.onCourseListener = onCourseListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.course_item, viewGroup, false);
        return new ViewHolder(view, onCourseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Course course = listOfCourses.get(i);
        viewHolder.txv_title.setText(course.getTitle());
        viewHolder.txv_description.setText(course.getDescription());
//        viewHolder.card_course.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), BookingActivity.class);
//                intent.putExtra(EXTRA_COURSE, course.getTitle());
//                v.getContext().startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listOfCourses.size();
    }

    public interface OnCourseListener {
        void onItemClicked(Course course);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txv_title;
        TextView txv_description;
        CardView card_course;
        OnCourseListener onCourseListener;

        ViewHolder(@NonNull View itemView, OnCourseListener onCourseListener) {
            super(itemView);
            txv_title = itemView.findViewById(R.id.txv_title);
            txv_description = itemView.findViewById(R.id.txv_description);
            card_course = itemView.findViewById(R.id.card_course);
            this.onCourseListener = onCourseListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCourseListener.onItemClicked(listOfCourses.get(getAdapterPosition()));
        }
    }
}
