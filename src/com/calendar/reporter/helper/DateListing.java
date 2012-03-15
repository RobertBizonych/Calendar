package com.calendar.reporter.helper;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.calendar.reporter.Tabs;

import java.util.Calendar;

public class DateListing{
    private Activity activity;
    private Session session;
    private static final int TABS = 0;
    private int tabType;

    public DateListing(Activity activity, Session session, int dateType){
        this.activity = activity;
        this.session = session;
        this.tabType = dateType;
    }

    public void lowerLeftBehavior(int id) {
        Button lowerLeftButton = (Button) activity.findViewById(id);
        lowerLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementDay(view, -1, Calendar.DATE);
            }
        });
    }

    public void lowerRightBehavior(int id) {
        Button lowerRightButton = (Button) activity.findViewById(id);
        lowerRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementDay(view, 1, Calendar.DATE);
            }
        });
    }

    public void upperLeftBehavior(int id) {
        Button upperLeftButton = (Button) activity.findViewById(id);
        upperLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementDay(view, -1, Calendar.MONTH);
            }
        });
    }

    public void upperRightBehavior(int id) {
        Button upperRightButton = (Button) activity.findViewById(id);
        upperRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementDay(view, 1, Calendar.MONTH);
            }
        });
    }

    private void incrementDay(View view, int step, int type) {
        LocalDate localDate = new LocalDate(session.getDate(tabType));
        String date = localDate.increment(step, type);

        session.setDate(date,tabType);
        session.setTabID(tabType);
        Log.e("session.getDate() Tab " + tabType, session.getDate(tabType));

        Intent cross = new Intent(view.getContext(), Tabs.class);
        activity.startActivityForResult(cross, TABS);
    }

}
