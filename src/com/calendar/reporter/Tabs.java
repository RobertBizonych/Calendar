package com.calendar.reporter;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TabHost;
import com.calendar.reporter.helper.Session;

public class Tabs extends TabActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, Tasks.class);

        spec = tabHost.newTabSpec("relevant").setIndicator("Relevant",null).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, TasksGeneral.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        spec = tabHost.newTabSpec("general").setIndicator("General",null).setContent(intent);
        tabHost.addTab(spec);

        SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
        Session session = new Session(settings);
        tabHost.setCurrentTab(session.getTabID());

    }
}