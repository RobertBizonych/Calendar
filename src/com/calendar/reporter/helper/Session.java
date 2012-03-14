package com.calendar.reporter.helper;

import android.content.SharedPreferences;

public class Session {
    private long projectId = 0;
    private long userId = 0;
    public static final String PREFS_NAME = "MyPrefsFile";
    private SharedPreferences.Editor editor;
    private SharedPreferences settings;

    public Session(SharedPreferences settings){
        this.settings = settings;
        this.editor = settings.edit();
    }

    public long getUserId() {
        userId = settings.getLong("session",0);
        return userId;
    }

    public void setUserId(long userId) {
        editor.putLong("session", userId);
        editor.commit();
    }

    public long getProjectId() {
        this.projectId = settings.getLong("projectId",0);
        return projectId;
    }

    public void setProjectId(long projectId) {
        editor.putLong("projectId", projectId);
        editor.commit();
    }
}
