package com.projects.tutofire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.projects.tutofire.R;
import com.projects.tutofire.UserFragment;
import com.projects.tutofire.fragments.CoursesFragment;
import com.projects.tutofire.fragments.ReservationsFragment;
import com.projects.tutofire.fragments.TeachersFragment;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    ImageButton btn_courses;
    ImageButton btn_teachers;
    ImageButton btn_reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        btn_courses = findViewById(R.id.home_btn_course);
        btn_teachers = findViewById(R.id.home_teachers);
        btn_reservations = findViewById(R.id.home_reservations);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btn_reservations.setOnClickListener(v -> {
            setInvisible();
            FragmentManager fm = getSupportFragmentManager();
            ReservationsFragment fragment = new ReservationsFragment();
            fm.beginTransaction().replace(R.id.container_home, fragment).commit();
        });
        btn_courses.setOnClickListener(v -> {
            setInvisible();
            FragmentManager fm = getSupportFragmentManager();
            CoursesFragment fragment = new CoursesFragment();
            fm.beginTransaction().replace(R.id.container_home, fragment).commit();
        });
        btn_teachers.setOnClickListener(v -> {
            setInvisible();
            FragmentManager fm = getSupportFragmentManager();
            TeachersFragment fragment = new TeachersFragment();
            fm.beginTransaction().replace(R.id.container_home, fragment).commit();
        });
    }

    private void setInvisible() {
        btn_reservations.setVisibility(View.INVISIBLE);
        btn_courses.setVisibility(View.INVISIBLE);
        btn_teachers.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        setInvisible();

        if (id == R.id.nav_courses) {
            FragmentManager fm = getSupportFragmentManager();
            CoursesFragment fragment = new CoursesFragment();
            fm.beginTransaction().replace(R.id.container_home, fragment).commit();
        } else if (id == R.id.nav_teachers) {
            FragmentManager fm = getSupportFragmentManager();
            TeachersFragment fragment = new TeachersFragment();
            fm.beginTransaction().replace(R.id.container_home, fragment).commit();
        } else if (id == R.id.nav_booked_lessons) {
            FragmentManager fm = getSupportFragmentManager();
            ReservationsFragment fragment = new ReservationsFragment();
            fm.beginTransaction().replace(R.id.container_home, fragment).commit();
        } else if (id == R.id.nav_send) {
            //todo new intent email
        } else if (id == R.id.account) {
            FragmentManager fm = getSupportFragmentManager();
            UserFragment fragment = new UserFragment();
            fm.beginTransaction().replace(R.id.container_home, fragment).commit();
        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
