package com.calendar.reporter.database.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.calendar.reporter.database.DataBaseHelper;
import com.calendar.reporter.helper.Messenger;

import java.util.ArrayList;
import java.util.List;

public class ProjectDataSource {
    private DataBaseHelper dbHelper;

    private String[] allColumns = {
            ProjectStructure.COLUMN_ID,
            ProjectStructure.COLUMN_NAME,
            ProjectStructure.COLUMN_DESCRIPTION,
            ProjectStructure.USER_ID
    };

    public ProjectDataSource(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public ProjectStructure createProject(String name, String description, long userId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(ProjectStructure.USER_ID, userId);
            values.put(ProjectStructure.COLUMN_NAME, name);
            values.put(ProjectStructure.COLUMN_DESCRIPTION, description);
            long insertId = database.insert(ProjectStructure.TABLE_NAME, null,
                    values);
            Cursor cursor = database.query(ProjectStructure.TABLE_NAME,
                    allColumns, ProjectStructure.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            Messenger messenger = new Messenger(ProjectDataSource.class.getName());
            ProjectStructure project = cursorToProject(cursor);
            messenger.debug("id " + project.getId() + " user_id " + project.getUserId());
            return project;
        } finally {
            database.close();
        }
    }

    private ProjectStructure cursorToProject(Cursor cursor) {
        ProjectStructure project = new ProjectStructure();
        project.setId(cursor.getLong(0));
        project.setName(cursor.getString(1));
        project.setDescription(cursor.getString(2));
        project.setUserId(cursor.getLong(3));
        return project;
    }

    public List<ProjectStructure> getAllProjects(long userId) {
        Messenger messenger = new Messenger(ProjectDataSource.class.getName());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        messenger.error("user id " + userId);

        try {
            List<ProjectStructure> projects = new ArrayList<ProjectStructure>();
            Cursor cursor = database.query(ProjectStructure.TABLE_NAME,
                    allColumns, ("user_id = " + userId), null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ProjectStructure project = cursorToProject(cursor);
                projects.add(project);
                cursor.moveToNext();
            }
            cursor.close();
            return projects;
        } finally {
            database.close();
        }
    }

    public ProjectStructure getNAProject() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = database.query(ProjectStructure.TABLE_NAME,
                    allColumns, ("name is 'N/A'"), null, null, null, null);
            cursor.moveToFirst();
            ProjectStructure project = cursorToProject(cursor);
            cursor.close();
            return project;
        }
        finally {
            database.close();
        }
    }
}
