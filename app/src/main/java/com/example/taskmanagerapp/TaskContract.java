package com.example.taskmanagerapp;

import android.provider.BaseColumns;

public class TaskContract {
    private TaskContract() {
    }

    public static final class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DUE_DATE = "due_date";
    }

}

