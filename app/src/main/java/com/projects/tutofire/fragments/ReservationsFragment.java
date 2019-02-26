package com.projects.tutofire.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.projects.tutofire.R;
import com.projects.tutofire.SharedViewModel;
import com.projects.tutofire.database.entity.Reservation;
import com.projects.tutofire.database.repository.ReservationsRepo;
import com.projects.tutofire.fragments.adapters.ReservationAdapter;


public class ReservationsFragment extends Fragment implements ReservationAdapter.OnReservationListener {
    private static final String TAG = "mTAG_Reservations";
    SharedViewModel vm;
    RecyclerView rec_reservations;
    private ReservationAdapter customAdapter;

    public ReservationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rec_reservations = getView().findViewById(R.id.rec_reservations);
        vm = ViewModelProviders.of(this).get(SharedViewModel.class);
        vm.getResrvations().observe(
                this, reservations -> {
                    Log.d(TAG, "onChanged() called with: courses = [" + reservations + "]");
                    customAdapter = new ReservationAdapter(reservations, this);
                    rec_reservations.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rec_reservations.setAdapter(customAdapter);

                    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            if (direction == ItemTouchHelper.RIGHT) {
                                customAdapter.deleteItem(viewHolder.getAdapterPosition());
                                customAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            } else {
                                customAdapter.cancel(viewHolder.getAdapterPosition());
                            }

                        }
                    }).attachToRecyclerView(rec_reservations);
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservations, container, false);
    }

    @Override
    public void onItemClicked(Reservation reservation) {
        Toast.makeText(getContext(), "clicked" + reservation, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setOnItemSelectedListener(Reservation reservation, int adapterPosition) {
        Toast.makeText(getContext(), "newStatus: " + reservation.getStatus(), Toast.LENGTH_SHORT).show();
        ReservationsRepo repo = new ReservationsRepo();
        repo.update(reservation);
        customAdapter.notifyItemChanged(adapterPosition);
    }

}
