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

    OnTeacherListener onTeacherListener;
    private List<Teacher> listOfTeachers;

    public TeacherAdapter(List<Teacher> teachers, OnTeacherListener onTeacherListener) {
        this.listOfTeachers = teachers;
        this.onTeacherListener = onTeacherListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.teacher_list_item, viewGroup, false);
        return new ViewHolder(view, onTeacherListener);//todo add oncick interface
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Teacher teacher = listOfTeachers.get(i);
        viewHolder.txv_name_teacher.setText(teacher.getUsername());
    }

    @Override
    public int getItemCount() {
        return listOfTeachers.size();
    }

    public interface OnTeacherListener {
        void onItemClicked(Teacher teacher);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txv_name_teacher;
        OnTeacherListener onTeacherListener;

        public ViewHolder(@NonNull View itemView, OnTeacherListener onTeacherListener) {
            super(itemView);
            txv_name_teacher = itemView.findViewById(R.id.txv_name_teacher);
            this.onTeacherListener = onTeacherListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("mTAG", "onClick:-------------------- ");
            onTeacherListener.onItemClicked(listOfTeachers.get(getAdapterPosition()));
        }
    }
}
