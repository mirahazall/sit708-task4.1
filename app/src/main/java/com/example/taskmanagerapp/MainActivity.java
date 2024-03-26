package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.taskmanagerapp.R;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity  {
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        TextView homeNav = findViewById(R.id.home_nav);
        homeNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        TextView viewNav = findViewById(R.id.view_nav);
        viewNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewTasksActivity.class));
            }
        });

        TextView addNav = findViewById(R.id.add_nav);
        addNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTasksActivity.class));
            }
        });



        TextView editNav = findViewById(R.id.edit_nav);
        editNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditTasksActivity.class));
            }

        });

       /* bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                return true;
            } else if (itemId == R.id.navigation_view_tasks) {
                startActivity(new Intent(MainActivity.this, ViewTasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_add_task) {
                startActivity(new Intent(MainActivity.this, AddTasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_edit_task) {
                startActivity(new Intent(MainActivity.this, EditTasksActivity.class));
                return true;
            }
            return false;
        });

        */

        Button viewTasksButton = findViewById(R.id.viewTasksButton);
        Button addTasksButton = findViewById(R.id.addTasksButton);
        Button editTasksButton = findViewById(R.id.editTasksButton);

        viewTasksButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ViewTasksActivity.class);
                startActivity(intent);

            }
        });

        addTasksButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, AddTasksActivity.class);
                startActivity(intent);
            }
        });

        editTasksButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, EditTasksActivity.class);
                startActivity(intent);
            }
        });

        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }
