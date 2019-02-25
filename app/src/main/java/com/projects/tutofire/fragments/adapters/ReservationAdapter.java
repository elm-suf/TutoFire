package com.projects.tutofire.fragments.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.tutofire.R;
import com.projects.tutofire.database.entity.Reservation;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
    private final List<Reservation> reservations;
    private final OnReservationListener listener;

    public ReservationAdapter(List<Reservation> reservations, OnReservationListener listener) {
        this.reservations = reservations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reservation_item, viewGroup, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Reservation reservation = reservations.get(i);
        viewHolder.title_reservation.setText(reservation.getCourse());
        viewHolder.teacher_reservation.setText(reservation.getTeacher());
        viewHolder.date_reservation.setText(reservation.getDate());
        viewHolder.time_reservation.setText(reservation.getTimeSlot());
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }


    public interface OnReservationListener {
        void onItemClicked(Reservation reservation);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnReservationListener listener;
        TextView title_reservation;
        TextView teacher_reservation;
        TextView date_reservation;
        TextView time_reservation;

        public ViewHolder(@NonNull View itemView, OnReservationListener listener) {
            super(itemView);
            title_reservation = itemView.findViewById(R.id.title_reservation);
            teacher_reservation = itemView.findViewById(R.id.teacher_reservation);
            date_reservation = itemView.findViewById(R.id.date_reservation);
            time_reservation = itemView.findViewById(R.id.time_reservation);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClicked(reservations.get(getAdapterPosition()));
        }
    }
}
