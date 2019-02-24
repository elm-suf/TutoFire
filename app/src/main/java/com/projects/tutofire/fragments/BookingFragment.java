package com.projects.tutofire.fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.projects.tutofire.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class BookingFragment extends Fragment {
    final String TAG = "mTAG";
    FirebaseFirestore db;
    FirebaseAuth mAuth;


    final Calendar myCalendar = Calendar.getInstance();
    String teacherId;
    String teacherUsername;
    String courseTitle;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    EditText edt_date_pick;
    TextView txv_booking;
    RadioGroup radioGroup;
    String date;


    public BookingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        teacherId = arguments.getString("id");
        courseTitle = arguments.getString("title");
        teacherUsername = arguments.getString("teacher");


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        date = DateFormat.format("yyyy-MM-dd", new Date()).toString();
//        available = vm.getAvailableSlots(teacherId, date);
//        Toast.makeText(getContext(), available.toString(), Toast.LENGTH_SHORT).show();

        radioGroup = getView().findViewById(R.id.radioGroup);
        txv_booking = getView().findViewById(R.id.txv_booking);
        edt_date_pick = getView().findViewById(R.id.edt_date_pick);
        btn_1 = getView().findViewById(R.id.btn_slot_1);
        btn_2 = getView().findViewById(R.id.btn_slot_2);
        btn_3 = getView().findViewById(R.id.btn_slot_3);
        btn_4 = getView().findViewById(R.id.btn_slot_4);

        btn_1.setOnClickListener(v -> bookLesson(btn_1, "15:00"));
        btn_2.setOnClickListener(v -> bookLesson(btn_2, "16:00"));
        btn_3.setOnClickListener(v -> bookLesson(btn_3, "17:00"));
        btn_4.setOnClickListener(v -> bookLesson(btn_4, "18:00"));

        edt_date_pick.setText(date);
        showAvailable(date);

        DatePickerDialog.OnDateSetListener mDateSetListener = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);

            date = year + "-" + month + "-" + day;

            updateLabel();
        };

        edt_date_pick.setOnClickListener(v -> {
            new DatePickerDialog(
                    v.getContext(), mDateSetListener,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
                    .show();
        });
    }

    @SuppressLint("ResourceAsColor")
    private void bookLesson(Button btn, String time_slot) {
        Toast.makeText(getContext(), "click time-slot " + time_slot, Toast.LENGTH_SHORT).show();
        btn.setBackgroundColor(Color.parseColor("#af4646"));
        btn.setTextColor(Color.parseColor("#d2b81e"));
        btn.setClickable(false);
        setTeacherUnavailable(teacherId, date, time_slot);
        addReservationToStudentCollection(date, time_slot);
        disable(btn);
    }

    private void addReservationToStudentCollection(String date, String time_slot) {
        Map<String, Object> data = new HashMap<>();
        data.put("teacherId", teacherId);
        data.put("teacher", teacherId);
        data.put("course", courseTitle);
        data.put("date", date);
        data.put("time_slot", time_slot);


        db.collection("users")
                .document(mAuth.getUid())
                .collection("reservations")
                .add(data);
    }

    private void setTeacherUnavailable(String teacher_id, String date, String time_slot) {
        Map<String, Object> data = new HashMap<>();
        data.put(time_slot, false);

        db.collection("teachers")
                .document(teacher_id)
                .collection("available")
                .document(date)
                .set(data, SetOptions.merge());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edt_date_pick.setText(sdf.format(myCalendar.getTime()));

        showAvailable(sdf.format(myCalendar.getTime()));
    }

    private void showAvailable(String format) {
        db.collection("teachers")
                .document(teacherId)
                .collection("available")
                .document(format)
                .get()
                .addOnCompleteListener(this::onComplete);
    }

    private void disable(Button btn) {
        btn.setBackgroundColor(Color.parseColor("#af4646"));
        btn.setTextColor(Color.parseColor("#d2b81e"));
        btn.setClickable(false);
    }

    private void enable(Button btn) {
        btn.setBackgroundColor(Color.parseColor("#49a37a"));
        btn.setTextColor(Color.parseColor("#424242"));
        btn.setClickable(true);
    }

    private void onComplete(Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
            Map<String, Object> data = Objects.requireNonNull(task.getResult()).getData();
            if (data != null) {
                for (String k : data.keySet()) {
                    System.out.println("key = " + k + ":" + data.get(k));
                    switch (k) {
                        case "15:00":
                            if ((boolean) data.get(k) == false) {
                                disable(btn_1);
                            } else {
                                enable(btn_1);
                            }
                            break;
                        case "16:00":
                            if ((boolean) data.get(k) == false) {
                                disable(btn_2);
                            } else {
                                enable(btn_2);
                            }
                            break;
                        case "17:00":
                            if ((boolean) data.get(k) == false) {
                                disable(btn_3);
                            } else {
                                enable(btn_3);
                            }
                            break;
                        case "18:00":
                            if ((boolean) data.get(k) == false) {
                                disable(btn_4);
                            } else {
                                enable(btn_4);
                            }
                            break;
                    }
                }
            } else {
                enable(btn_1);
                enable(btn_2);
                enable(btn_3);
                enable(btn_4);
            }
        }
    }
}
