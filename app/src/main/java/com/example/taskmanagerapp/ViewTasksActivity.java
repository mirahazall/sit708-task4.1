package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.taskmanagerapp.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ViewTasksActivity extends AppCompatActivity {
    Button backToMainActivityButton;
    private BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tasks);

       /* bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(ViewTasksActivity.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.navigation_view_tasks) {
                startActivity(new Intent(ViewTasksActivity.this, ViewTasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_add_task) {
                startActivity(new Intent(ViewTasksActivity.this, AddTasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_edit_task) {
                startActivity(new Intent(ViewTasksActivity.this, EditTasksActivity.class));
                return true;
            }
            return false;
        });

        */

        ListView listViewTasks = findViewById(R.id.listViewTasks);
        // Retrieve tasks from the database
        TaskDataSource dataSource = new TaskDataSource(this);
        dataSource.open();
        List<Task> tasks = dataSource.getAllTasks();
        dataSource.close();

        // Create adapter for the ListView
        ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);

        // Set adapter to the ListView
        listViewTasks.setAdapter(adapter);

        Button backToMainActivityButton = findViewById(R.id.backToMainActivityButton);
        backToMainActivityButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View w){
                Intent intent = new Intent(ViewTasksActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
