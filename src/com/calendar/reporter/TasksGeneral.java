package com.calendar.reporter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import com.calendar.reporter.helper.DateListing;
import com.calendar.reporter.helper.LocalDate;
import com.calendar.reporter.helper.Session;

public class TasksGeneral extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general);

        SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
        Session session = new Session(settings);

        LocalDate localDate = new LocalDate(session.getDate(Session.GENERAL));

        TextView lowerText = (TextView) findViewById(R.id.lowerTextGeneral);
        lowerText.setText(localDate.getDayName());

        TextView upperText = (TextView) findViewById(R.id.upperTextGeneral);
        upperText.setText(localDate.getMonthName());

        DateListing dateListing = new DateListing(this, session, Session.GENERAL);
        dateListing.upperRightBehavior(R.id.upperRightGeneral);
        dateListing.upperLeftBehavior(R.id.upperLeftGeneral);
        dateListing.lowerRightBehavior(R.id.lowerRightGeneral);
        dateListing.lowerLeftBehavior(R.id.lowerLeftGeneral);

    }
}