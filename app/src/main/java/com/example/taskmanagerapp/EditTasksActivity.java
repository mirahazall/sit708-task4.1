package com.example.taskmanagerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EditTasksActivity extends AppCompatActivity {

    ListView listViewTasks;
    ArrayList<Task> tasks;
    ArrayAdapter<Task> adapter;
    EditText taskTitleEditText, taskDescriptionEditText, dueDateEditText;
    Button backToMainActivityButton, updateTaskButton;
    TaskDataSource dataSource;
    Task taskToUpdate;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tasks);

        dataSource = new TaskDataSource(this);
        dataSource.open();
        tasks = (ArrayList<Task>) dataSource.getAllTasks();
        dataSource.close();

        backToMainActivityButton = findViewById(R.id.backToMainActivityButton);
        listViewTasks = findViewById(R.id.listViewTasks);
       /* bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(EditTasksActivity.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.navigation_view_tasks) {
                startActivity(new Intent(EditTasksActivity.this, ViewTasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_add_task) {
                startActivity(new Intent(EditTasksActivity.this, AddTasksActivity.class));
                return true;
            } else if (itemId == R.id.navigation_edit_task) {
                return true;
            }
            return false;
        });

        */


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listViewTasks.setAdapter(adapter);

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long listItemId) {
                Task selectedTask = tasks.get(position);
                // Populate EditText fields with selected task details
                // For simplicity, you can directly open a new activity to update the selected task
                Intent intent = new Intent(EditTasksActivity.this, UpdateTaskActivity.class);
                intent.putExtra("TASK_ID", selectedTask.getId());
                startActivity(intent);
            }
        });

        backToMainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTasksActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }


}







