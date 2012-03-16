package com.calendar.reporter.helper;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.calendar.reporter.R;

public class TableAdapter {
    private TableLayout tableLayout;
    private TableRow tableRow;

    private Activity activity;

    public TableAdapter(TableLayout tableLayout, Activity activity) {
        this.tableLayout = tableLayout;
        this.tableRow = new TableRow(activity);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
        this.activity = activity;
    }

    public void createRow(String activityName, String totalHour) {
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
        column.setBackgroundColor(activity.getResources().getColor(R.color.darkGrey));
        tableRow.addView(column);
    }

}
