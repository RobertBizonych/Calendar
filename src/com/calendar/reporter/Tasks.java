package com.calendar.reporter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.calendar.reporter.database.task.TaskDataSource;
import com.calendar.reporter.database.task.TaskStructure;
import com.calendar.reporter.helper.LocalDate;
import com.calendar.reporter.helper.Messenger;
import com.calendar.reporter.helper.Session;

import java.util.Calendar;
import java.util.List;

public class Tasks extends ListActivity {
    private ArrayAdapter<TaskStructure> adapter;
    private static final int TASK = 0;
    private static final int PROJECTS = 0;
    private static final int TABS = 0;
    private Session session;
    private final Context context = this;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);

        SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
        session = new Session(settings);

        TextView lowerText = (TextView) findViewById(R.id.lowerText);
        lowerText.setText(session.getLowerDateText());

        TextView upperText = (TextView) findViewById(R.id.upperText);
        upperText.setText(session.getUpperDateText());

        final TaskDataSource dataSource = new TaskDataSource(this);
        List<TaskStructure> tasks = dataSource.getAllTasks(session.getProjectId(), session.getDate());
        final ArrayAdapter<TaskStructure> adapter = new ArrayAdapter<TaskStructure>(this,
                android.R.layout.simple_list_item_1, tasks);
        setListAdapter(adapter);
//        AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
//            
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TaskStructure task = (TaskStructure) adapterView.getItemAtPosition(i);
//                Intent cross = new Intent(view.getContext(), Task.class);

//                cross.putExtra("type", "edit");
//                startActivityForResult(cross, TASK);
//            }
//        };
//        getListView().setOnItemClickListener(onItemLongClickListener);
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

        upperLeftBehavior();
        upperRightBehavior();
        lowerRightBehavior();
        lowerLeftBehavior();
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

    private void lowerLeftBehavior() {
        Button lowerLeftButton = (Button) findViewById(R.id.lowerLeft);
        lowerLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementDay(view, -1, Calendar.DATE);
            }
        });
    }

    private void lowerRightBehavior() {
        Button lowerRightButton = (Button) findViewById(R.id.lowerRight);
        lowerRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementDay(view, 1, Calendar.DATE);
            }
        });
    }

    private void upperLeftBehavior() {
        Button upperLeftButton = (Button) findViewById(R.id.upperLeft);
        upperLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementDay(view, -1, Calendar.MONTH);
            }
        });
    }

    private void upperRightBehavior() {
        Button upperRightButton = (Button) findViewById(R.id.upperRight);
        upperRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementDay(view, 1, Calendar.MONTH);
            }
        });
    }

    private void incrementDay(View view, int step, int type) {
        Messenger messenger = new Messenger(Tasks.class.getName());
        LocalDate localDate = new LocalDate(session.getDate());
        String date = localDate.increment(step, type);

        session.setLowerDateText(localDate.getDayName());
        session.setUpperDateText(localDate.getMonthName());
        session.setDate(date);
        messenger.error("session.getDate() " + session.getDate());

        Intent cross = new Intent(view.getContext(), Tabs.class);
        startActivityForResult(cross, TABS);
    }

}