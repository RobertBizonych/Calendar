package com.calendar.reporter.helper;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class Messenger {
    private Activity activity = null;
    private String className = "";

    public Messenger(Activity activity){
        this.activity = activity;
    }
    public Messenger(String className){
        this.className = className;
    }

    public Messenger(Activity activity, String className) {
        this.activity = activity;
        this.className = className;
    }

    public void alert(String message){
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public void info(String message){
        Log.i(className, message);
    }

    public void error(String message){
        Log.e(className, message);
    }

    public void debug(String message) {
        Log.d(className, message);
    }
}
