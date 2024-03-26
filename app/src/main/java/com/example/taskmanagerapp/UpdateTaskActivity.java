package com.example.taskmanagerapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateTaskActivity extends AppCompatActivity {

    EditText taskTitleEditText, taskDescriptionEditText, dueDateEditText;
    Button updateButton;
    TaskDataSource dataSource;
    Task taskToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_task);
        Button backToMainActivityButton = findViewById(R.id.backToMainActivityButton);
        backToMainActivityButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View w){
                Intent intent = new Intent(UpdateTaskActivity.this, EditTasksActivity.class);
                startActivity(intent);
            }
        });

        dataSource = new TaskDataSource(this);
        dataSource.open();

        taskTitleEditText = findViewById(R.id.taskTitleEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);


        // Get the task ID from Intent extras
        long taskId = getIntent().getLongExtra("TASK_ID", -1);
        if (taskId != -1) {
            // Retrieve the task details from the database
            taskToUpdate = dataSource.getTaskById(taskId);
            if (taskToUpdate != null) {
                // Populate EditText fields with task details
                taskTitleEditText.setText(taskToUpdate.getTitle());
                taskDescriptionEditText.setText(taskToUpdate.getDescription());
                dueDateEditText.setText(taskToUpdate.getDueDate());
            } else {
                // Task not found, handle error or notify user
                Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show();
                finish(); // Close this activity
            }
        } else {
            // Task ID not provided, handle error or notify user
            Toast.makeText(this, "Task ID not provided", Toast.LENGTH_SHORT).show();
            finish(); // Close this activity
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get updated task details from EditText fields
                String newTitle = taskTitleEditText.getText().toString();
                String newDescription = taskDescriptionEditText.getText().toString();
                String newDueDate = dueDateEditText.getText().toString();

                // Update the task in the database
                dataSource.updateTask(taskToUpdate.getId(), newTitle, newDescription, newDueDate);

// Show a toast message indicating the task has been updated
                Toast.makeText(UpdateTaskActivity.this, "Task updated successfully", Toast.LENGTH_SHORT).show();


                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a confirmation dialog before deleting the task
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
                builder.setTitle("Delete Task");
                builder.setMessage("Are you sure you want to delete this task?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete the task from the database
                        dataSource.deleteTask(taskToUpdate.getId());
                        // Show a toast message indicating the task has been deleted
                        Toast.makeText(UpdateTaskActivity.this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                        // Optionally, you can navigate back to the previous activity
                        finish();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}

