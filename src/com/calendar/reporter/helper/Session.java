package com.calendar.reporter.helper;

import android.content.SharedPreferences;
import com.calendar.reporter.database.task.TaskStructure;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Session {
    private long projectId = 0;
    private long userId = 0;
    private int tabID;
    private String date;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final int RELEVANT = 0;
    public static final int GENERAL = 1;

    private SharedPreferences.Editor editor;
    private SharedPreferences settings;
    private String defaultDate;

    public Session(SharedPreferences settings){
        this.settings = settings;
        this.editor = settings.edit();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TaskStructure.DATE_FORMAT);
        this.defaultDate = sdf.format(cal.getTime());
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

    public void setDate(String date, int type) {
        switch(type){
            case 0:
                editor.putString("relevantDate", date);
                break;
            case 1:
                editor.putString("generalDate", date);
                break;
        }
        editor.commit();
    }
    public String getDate(int type){
        switch(type){
               case 0:
                   this.date = settings.getString("relevantDate", defaultDate);
                   break;
               case 1:
                   this.date = settings.getString("generalDate", defaultDate);
                   break;
        }
        return date;
    }

    public void resetDate() {
        setDate(defaultDate, RELEVANT);
        setDate(defaultDate, GENERAL);
    }

    public void reset(){
        setUserId(0);
        setProjectId(0);
        setTabID(0);
        resetDate();
    }

    public int getTabID() {
        this.tabID = settings.getInt("tabID", 0);
        return tabID;
    }

    public void setTabID(int tabID) {
        editor.putInt("tabID", tabID);
        editor.commit();
    }
}
