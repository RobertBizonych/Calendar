package com.calendar.reporter.database.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.calendar.reporter.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ActivityDataSource {
    private DataBaseHelper dbHelper;

    private String[] allColumns = {
            ActivityStructure.COLUMN_ID,
            ActivityStructure.COLUMN_NAME
    };

    public ActivityDataSource(Context context) {
        dbHelper = new DataBaseHelper(context);
    }
    public List<ActivityStructure> getAllActivities(){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<ActivityStructure> activities = null;
        try{
            activities = new ArrayList<ActivityStructure>();
            Cursor cursor = database.query(ActivityStructure.TABLE_NAME,
                    allColumns, null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                ActivityStructure activity = cursorToActivity(cursor);
                activities.add(activity);
                cursor.moveToNext();
            }
            cursor.close();
        }finally {
            database.close();
        }
        return activities;
    }

    private ActivityStructure cursorToActivity(Cursor cursor) {
        ActivityStructure activity = new ActivityStructure();
        activity.setId(cursor.getLong(0));
        activity.setName(cursor.getString(1));
        return activity;
    }
}
