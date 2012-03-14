package com.calendar.reporter.database.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.calendar.reporter.database.DataBaseHelper;
import com.calendar.reporter.database.project.ProjectStructure;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TaskDataSource {
    private DataBaseHelper dbHelper;

    private String[] allColumns = {
            TaskStructure.COLUMN_ID,
            TaskStructure.COLUMN_NAME,
            TaskStructure.COLUMN_DESCRIPTION,
            TaskStructure.COLUMN_DATE,
            TaskStructure.COLUMN_TIME,
            TaskStructure.ACTIVITY_ID,
            TaskStructure.PROJECT_ID
    };

    public TaskDataSource(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public TaskStructure createTask(String name, String description, String date, int time, long activityId, long projectId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();

            values.put(TaskStructure.COLUMN_NAME, name);
            values.put(TaskStructure.COLUMN_DESCRIPTION, description);
            values.put(TaskStructure.COLUMN_DATE, date);
            values.put(TaskStructure.COLUMN_TIME, time);
            values.put(TaskStructure.ACTIVITY_ID, activityId);
            values.put(TaskStructure.PROJECT_ID, projectId);
            long insertId = database.insert(TaskStructure.TABLE_NAME, null,
                    values);
            Cursor cursor = database.query(TaskStructure.TABLE_NAME,
                    allColumns, TaskStructure.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            TaskStructure task = cursorToTask(cursor);
            return task;
        } finally {
            database.close();
        }
    }

    private TaskStructure cursorToTask(Cursor cursor) {
        TaskStructure task = new TaskStructure();
        task.setId(cursor.getLong(0));
        task.setName(cursor.getString(1));
        task.setDescription(cursor.getString(2));
        task.setDate(StringToDate(cursor.getString(3)));
        task.setTime(cursor.getInt(4));
        task.setActivityID(cursor.getLong(5));
        task.setProjectID(cursor.getLong(6));
        return task;
    }

    private Date StringToDate(String stringDate) {
        DateFormat formater = new SimpleDateFormat(TaskStructure.DATE_FORMAT);
        try {
            java.util.Date parsedUtilDate = formater.parse(stringDate);
            Date sqltDate= new Date(parsedUtilDate.getTime());
            return sqltDate;
        } catch (ParseException e) {
            throw new Error(e.getMessage());
        }
    }

    public List<TaskStructure> getAllTasks(long projectId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        List<TaskStructure> projects = null;
        try {
            projects = new ArrayList<TaskStructure>();
            Cursor cursor = database.query(TaskStructure.TABLE_NAME,
                    allColumns, ("project_id = " + projectId), null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TaskStructure task = cursorToTask(cursor);
                projects.add(task);
                cursor.moveToNext();
            }
            cursor.close();
        } finally {
            database.close();
        }
        return projects;
    }



    public TaskStructure getTask(long taskId) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = database.query(TaskStructure.TABLE_NAME,
                    allColumns, ("_id = " + taskId), null, null, null, null);
            cursor.moveToFirst();
            TaskStructure task = cursorToTask(cursor);
            cursor.close();
            return task;
        } finally {
            database.close();
        }
    }

    public int updateTask(String nameTask, String descriptionTask, long taskId) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(TaskStructure.COLUMN_NAME, nameTask);
            values.put(TaskStructure.COLUMN_DESCRIPTION,descriptionTask);
            String whereClause = ("_id = " + taskId);
            return database.update(TaskStructure.TABLE_NAME,values,whereClause,null);
        }
        finally {
            database.close();
        }
    }
}
