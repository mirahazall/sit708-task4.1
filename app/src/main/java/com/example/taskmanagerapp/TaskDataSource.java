package com.example.taskmanagerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.util.Log;

public class TaskDataSource {
    private SQLiteDatabase database;
    private TaskDbHelper dbHelper;

    public TaskDataSource(Context context) {
        dbHelper = new TaskDbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertTask(String title, String description, String dueDate) {

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TITLE, title);
        values.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, description);
        values.put(TaskContract.TaskEntry.COLUMN_DUE_DATE, dueDate);

        long insertedId = database.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
        Log.d("TaskDataSource", "Inserted task with ID: " + insertedId);

        return insertedId;
    }


    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = database.query(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                TaskContract.TaskEntry.COLUMN_DUE_DATE + " ASC" // Sort by due date in ascending order
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    private Task cursorToTask(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(TaskContract.TaskEntry._ID); // Retrieve column index for task ID
        int titleIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TITLE);
        int descriptionIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION);
        int dueDateIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DUE_DATE);

        // Check if column indices are valid
        if (titleIndex != -1 && descriptionIndex != -1 && dueDateIndex != -1) {
            long id = cursor.getLong(idIndex); // Retrieve task ID
            // Retrieve column values
            String title = cursor.getString(titleIndex);
            String description = cursor.getString(descriptionIndex);
            String dueDate = cursor.getString(dueDateIndex);

            return new Task(id, title, description, dueDate);

        } else {
            return null; 
        }
    }

    public void updateTask(long taskId, String newTitle, String newDescription, String newDueDate) {
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TITLE, newTitle);
        values.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, newDescription);
        values.put(TaskContract.TaskEntry.COLUMN_DUE_DATE, newDueDate);

        String selection = TaskContract.TaskEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(taskId) };

        database.update(
                TaskContract.TaskEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    public void deleteTask(long taskId) {
        database.delete(
                TaskContract.TaskEntry.TABLE_NAME,
                TaskContract.TaskEntry._ID + " = ?",
                new String[]{String.valueOf(taskId)}
        );
    }

    public Task getTaskById(long taskId) {
        Log.d("TaskDataSource", "Fetching task for ID: " + taskId); // Log the task ID
        Task task = null;
        Cursor cursor = database.query(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                TaskContract.TaskEntry._ID + " = ?",
                new String[]{String.valueOf(taskId)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("TaskDataSource", "Found task for ID: " + taskId); // Log if task found
            task = cursorToTask(cursor);
            cursor.close();
        }else{
            Log.e("TaskDataSource", "No task found for ID: " + taskId); // Log task not found
        }

        return task;
    }
}
