package com.calendar.reporter;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class Tabs extends TabActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        Bundle bundle = getIntent().getExtras();
        long userId = bundle.getLong("session");
        long projectId = bundle.getLong("projectId");

        intent = new Intent().setClass(this, Tasks.class);

        spec = tabHost.newTabSpec("relevant").setIndicator("Relevant",null).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, TasksGeneral.class);
        spec = tabHost.newTabSpec("general").setIndicator("General",null).setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}