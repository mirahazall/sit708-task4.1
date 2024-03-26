package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class AddTasksActivity extends AppCompatActivity {
    private EditText taskTitleEditText, taskDescriptionEditText, dueDateEditText;
    private TaskDataSource dataSource;
    Button backToMainActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tasks);

        Button backToMainActivityButton = findViewById(R.id.backToMainActivityButton);
        backToMainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                Intent intent = new Intent(AddTasksActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        dataSource = new TaskDataSource(this);
        dataSource.open();

        // Initialize views
        taskTitleEditText = findViewById(R.id.taskTitleEditView);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditView);
        dueDateEditText = findViewById(R.id.dueDateEditTextDate);
        Button createTaskButton = findViewById(R.id.createTaskButton);

        createTaskButton.setOnClickListener(v -> {
            String title = taskTitleEditText.getText().toString();
            String description = taskDescriptionEditText.getText().toString();
            String dueDate = dueDateEditText.getText().toString();

            // Validate the input
            if (validateInput(title, description, dueDate)) {
                long insertedId = dataSource.insertTask(title, description, dueDate);
                if (insertedId != -1) {
                    // Task successfully inserted
                    Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
                    // Navigate back to the main activity
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close this activity to prevent going back to it when pressing back
                } else {
                    // Error inserting task
                    Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        private boolean validateInput (String title, String description, String dueDate){
            // Trim the input strings to remove leading and trailing whitespace
            title = title.trim();
            description = description.trim();
            dueDate = dueDate.trim();

            if (title.isEmpty()) {
                // Title is empty
                taskTitleEditText.setError("Title cannot be empty");
                return false;
            }

            if (description.isEmpty()) {
                // Description is empty
                taskDescriptionEditText.setError("Description cannot be empty");
                return false;
            }

            if (dueDate.isEmpty()) {
                // Due date is empty
                dueDateEditText.setError("Due date cannot be empty");
                return false;
            }

            return true;
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
            dataSource.close();
        }
}

