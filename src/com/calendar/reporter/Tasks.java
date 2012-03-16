package com.calendar.reporter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.calendar.reporter.database.task.TaskDataSource;
import com.calendar.reporter.database.task.TaskStructure;
import com.calendar.reporter.helper.DateListing;
import com.calendar.reporter.helper.LocalDate;
import com.calendar.reporter.helper.Messenger;
import com.calendar.reporter.helper.Session;

import java.util.Calendar;
import java.util.List;

public class Tasks extends ListActivity {
    private static final int TASK = 0;
    private static final int PROJECTS = 0;
    private static final int TABS = 0;
    private final Context context = this;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);

        SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
        Session session = new Session(settings);

        LocalDate localDate = new LocalDate(session.getDate(Session.RELEVANT));

        TextView lowerText = (TextView) findViewById(R.id.lowerTextRelevant);
        lowerText.setText(localDate.getDayName());

        TextView upperText = (TextView) findViewById(R.id.upperTextRelevant);
        upperText.setText(localDate.getMonthName());

        TextView launchText = (TextView) findViewById(android.R.id.empty);
        launchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cross = new Intent(view.getContext(), Task.class);
                cross.putExtra("type", "create new task");
                startActivityForResult(cross,TASK);
            }
        });

        final TaskDataSource dataSource = new TaskDataSource(this);
        List<TaskStructure> tasks = dataSource.getAllTasks(session.getProjectId(), session.getDate(Session.RELEVANT));
        final ArrayAdapter<TaskStructure> adapter = new ArrayAdapter<TaskStructure>(this,
                android.R.layout.simple_list_item_1, tasks);
        setListAdapter(adapter);

        DateListing dateListing = new DateListing(this, session, Session.RELEVANT);
        dateListing.upperRightBehavior(R.id.upperRightRelevant);
        dateListing.upperLeftBehavior(R.id.upperLeftRelevant);
        dateListing.lowerRightBehavior(R.id.lowerRightRelevant);
        dateListing.lowerLeftBehavior(R.id.lowerLeftRelevant);

        AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                final TaskStructure task = (TaskStructure) adapterView.getItemAtPosition(i);
                final CharSequence[] items = {"Create new task", "Edit","Delete"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose the action:");
                
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        switch (item) {
                            case 0:
                                Intent cross = new Intent(view.getContext(), Task.class);
                                cross.putExtra("type", "create new task");
                                startActivityForResult(cross,TASK);
                                break;
                            case 1:
                                Intent cross1 = new Intent(view.getContext(), Task.class);
                                cross1.putExtra("type", "edit");
                                cross1.putExtra("taskId", task.getId());
                                startActivityForResult(cross1, TASK);
                                break;
                            case 2:
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                                alertBuilder.setMessage("Do you want to remove " + task.getName() + "?");
                                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        adapter.remove(task);
                                        dataSource.deleteTask(task.getId());
                                        adapter.notifyDataSetChanged();

                                        Toast.makeText(getApplicationContext(), task.getName() +
                                                " has been removed.", Toast.LENGTH_LONG).show();
                                    }
                                });
                                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertBuilder.show();
                                break;
                        }
                    }
                });
                builder.show();
                return true;
            }
        };
        getListView().setOnItemLongClickListener(onItemLongClickListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent cross = new Intent(getApplicationContext(), Projects.class);
            startActivityForResult(cross, PROJECTS);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}