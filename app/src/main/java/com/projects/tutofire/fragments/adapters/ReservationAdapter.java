package com.projects.tutofire.fragments.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.projects.tutofire.R;
import com.projects.tutofire.database.entity.Reservation;
import com.projects.tutofire.database.repository.ReservationsRepo;

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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Reservation reservation = reservations.get(i);
        String status = reservation.getStatus();
        viewHolder.course_reserv_txv.setText(reservation.getCourse());
        viewHolder.teacher_reservation.setText(reservation.getTeacher());
        viewHolder.date_reservation.setText(reservation.getDate());
        viewHolder.time_reservation.setText(reservation.getTimeSlot());
        System.out.println("+++++++++++++++++++++++" + reservation.getStatus());
        if (status.equals("Active")) {
//            R.color.colorAccent
            viewHolder.view.setBackgroundColor(Color.parseColor("#d2b81e"));
            viewHolder.spinner.setSelection(0);
        } else if (status.equals("Cancelled")) {
            viewHolder.spinner.setSelection(1);
            viewHolder.view.setBackgroundColor(Color.parseColor("#78909C"));
        } else if (status.equals("Done")) {
            viewHolder.spinner.setSelection(2);
            viewHolder.view.setBackgroundColor(Color.parseColor("#49a37a"));
        }
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void deleteItem(int index) {
        ReservationsRepo repo = new ReservationsRepo();

        repo.delete(reservations.get(index));
        reservations.get(index).setStatus("cancelled");
//        reservations.remove(index);
    }

    public void cancel(int adapterPosition) {
        //todo implement method here
    }


    public interface OnReservationListener {
        void onItemClicked(Reservation reservation);

        void setOnItemSelectedListener(Reservation reservation, int adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterView.OnItemSelectedListener {
        OnReservationListener listener;
        TextView course_reserv_txv;
        TextView teacher_reservation;
        TextView date_reservation;
        TextView time_reservation;
        Spinner spinner;
        View view;

        public ViewHolder(@NonNull View itemView, OnReservationListener listener) {
            super(itemView);
            teacher_reservation = itemView.findViewById(R.id.teacher_reservation);
            course_reserv_txv = itemView.findViewById(R.id.course_reserv_txv);
            date_reservation = itemView.findViewById(R.id.date_reserv_txv);
            time_reservation = itemView.findViewById(R.id.time_reserv_txv);
            spinner = itemView.findViewById(R.id.spinner);
            view = itemView;


            ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource
                    (itemView.getContext(), R.array.status, android.R.layout.simple_spinner_item);

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(arrayAdapter);


            this.listener = listener;
            spinner.setOnItemSelectedListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClicked(reservations.get(getAdapterPosition()));
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String newStatus = (String) parent.getItemAtPosition(position);
            Reservation ret_val = reservations.get(getAdapterPosition());
            ret_val.setStatus(newStatus);
            listener.setOnItemSelectedListener(ret_val, getAdapterPosition());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
