package com.projects.tutofire.fragments.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.tutofire.R;
import com.projects.tutofire.database.entity.Teacher;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {

    private List<Teacher> teachers;

    public TeacherAdapter(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.teacher_list_item, viewGroup, false);
        return new ViewHolder(view);//todo add oncick interface
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Teacher teacher = teachers.get(i);
        viewHolder.txv_name_teacher.setText(teacher.getUsername());
        viewHolder.txv_rating.setText("22");
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txv_name_teacher;
        TextView txv_rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txv_name_teacher = itemView.findViewById(R.id.txv_name_teacher);
            txv_rating = itemView.findViewById(R.id.txv_rating);
        }

        @Override
        public void onClick(View v) {
            Log.d("mTAG", "onClick:-------------------- ");
        }
    }
}
