package com.projects.tutofire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.projects.tutofire.R;

import static com.projects.tutofire.fragments.adapters.CourseAdapter.EXTRA_COURSE;

public class BookingActivity extends AppCompatActivity {
    private TextView txv_title_booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Intent intent = getIntent();
        String courseTitle = intent.getStringExtra(EXTRA_COURSE);
        txv_title_booking = findViewById(R.id.txv_title_booking);

        txv_title_booking.setText(courseTitle);

    }
}
