package com.calendar.reporter.helper;

import android.content.SharedPreferences;
import com.calendar.reporter.database.task.TaskDataSource;
import com.calendar.reporter.database.task.TaskStructure;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Session {
    private long projectId = 0;
    private long userId = 0;
    private String date;
    private String lowerDateText;
    private String upperDateText;
    public static final String PREFS_NAME = "MyPrefsFile";
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

    public void setDate(String date) {
        editor.putString("date", date);
        editor.commit();
    }
    public String getDate(){
        this.date = settings.getString("date", defaultDate);
        return date;
    }

    public void resetDate() {
        setDate(defaultDate);
    }

    public String getLowerDateText() {
        this.lowerDateText = settings.getString("lowerDate", "Date");
        return lowerDateText;
    }

    public void setLowerDateText(String lowerDateText) {
        editor.putString("lowerDate", lowerDateText);
        editor.commit();
    }

    public String getUpperDateText() {
        this.upperDateText = settings.getString("upperDate", "Date");
        return upperDateText;
    }

    public void setUpperDateText(String upperDateText) {
        editor.putString("upperDate", upperDateText);
        editor.commit();
    }
}
