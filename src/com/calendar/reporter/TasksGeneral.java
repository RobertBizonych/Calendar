package com.calendar.reporter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.calendar.reporter.helper.DateListing;
import com.calendar.reporter.helper.LocalDate;
import com.calendar.reporter.helper.Session;
import android.widget.TableRow.LayoutParams;

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

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableGeneral);

        TableAdapter tableAdapter = new TableAdapter(tableLayout, this);
        tableAdapter.createRow("sss","100");

    }
    class TableAdapter{
        private TableLayout tableLayout;
        private TableRow tableRow;
        private Activity activity;
        
        public TableAdapter(TableLayout tableLayout, Activity activity){
            this.tableLayout = tableLayout;
            this.tableRow = new TableRow(activity);
            tableRow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
            this.activity = activity;
        }

        public void createRow(String activityName,String totalHour){
            createColumn(activityName);
            createColumn(totalHour);
            tableLayout.addView(tableRow);
        }
        
        private void createColumn(String text) {
            TextView column = new TextView(activity);
            column.setText(text);
            column.setTextColor(Color.BLACK);
            column.setPadding(0, 20, 0, 20);
            column.setGravity(Gravity.CENTER);
            column.setBackgroundColor(getResources().getColor(R.color.darkGrey));
            tableRow.addView(column);
        }

    }

}