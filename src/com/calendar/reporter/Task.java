package com.calendar.reporter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.calendar.reporter.database.activity.ActivityDataSource;
import com.calendar.reporter.database.activity.ActivityStructure;
import com.calendar.reporter.database.project.ProjectDataSource;
import com.calendar.reporter.database.task.TaskDataSource;
import com.calendar.reporter.database.task.TaskStructure;
import com.calendar.reporter.helper.HourPicker;
import com.calendar.reporter.helper.Messenger;
import com.calendar.reporter.helper.MinutePicker;
import com.calendar.reporter.helper.Session;

import java.util.List;

public class Task extends Activity {
    private ActivityStructure activity;
    public static final int TABS = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        final TaskDataSource dataSource = new TaskDataSource(this);

        Bundle bundle = getIntent().getExtras();
        SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
        final Session session = new Session(settings);

        final Spinner activityList = (Spinner) findViewById(R.id.activityList);
        ArrayAdapter<ActivityStructure> adapter = activityListAdapter();

        activityList.setAdapter(adapter);
        activityList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ActivityStructure act = (ActivityStructure) parent.getItemAtPosition(position);
                activity = act;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        }
        );

        final EditText taskName = (EditText) findViewById(R.id.taskName);
        final EditText taskDescription = (EditText) findViewById(R.id.taskDescription);
        final HourPicker hourPicker = (HourPicker) findViewById(R.id.hourPicker);
        final MinutePicker minutePicker = (MinutePicker) findViewById(R.id.minutePicker);
        final Messenger messenger = new Messenger(this);
        final String type = bundle.getString("type");
        final long taskId = bundle.getLong("taskId");

        Button createTask = (Button) findViewById(R.id.createTask);

        if (type != null && type.equals("edit")) {
            createTask.setText("Update");
            final TaskStructure task = dataSource.getTask(taskId);

            taskName.setText(task.getName());
            taskDescription.setText(task.getDescription());
            int time = task.getTime();

            hourPicker.setValue(time / 60);
            minutePicker.setValue(time % 60);
            createTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameTask = taskName.getText().toString();
                    String descriptionTask = taskDescription.getText().toString();

                    int pickHour = hourPicker.getValue();
                    int pickMinute = minutePicker.getValue();

                    if (!nameTask.equals("") && !descriptionTask.equals("") && activity != null && !(pickHour == 0 && pickMinute == 0)) {
                            int time = pickHour * 60 + pickMinute;
                            int status = dataSource.updateTask(nameTask, descriptionTask, time, taskId);
                            if (status == 1) {
                                messenger.alert("Task " + task.getName() + " was updated");
                                Intent cross = new Intent(view.getContext(), Tabs.class);
                                startActivityForResult(cross, TABS);
                            } else {
                                messenger.alert("Failed to update!");
                            }
                    }
                }
            });
        } else {
            createTask.setOnClickListener(new View.OnClickListener() {
                public static final int TABS = 0;

                @Override
                public void onClick(View view) {
                    String nameTask = taskName.getText().toString();
                    String descriptionTask = taskDescription.getText().toString();

                    int pickHour = hourPicker.getValue();
                    int pickMinute = minutePicker.getValue();

                    if (!nameTask.equals("") && !descriptionTask.equals("") && activity != null && !(pickHour == 0 && pickMinute == 0)) {
                        int time = pickHour * 60 + pickMinute;
                        if (dataSource.taskExists(nameTask, session)) {
                            messenger.alert("The task with name '" + nameTask + "' is reserved!");
                        } else {
                           TaskStructure task = dataSource.createTask(nameTask, descriptionTask, session.getDate(Session.RELEVANT), time,
                                    activity.getId(), session.getProjectId());
                            if (task != null) {
                                messenger.alert("Task " + task.getName() + " is successfully created");
                                Intent cross = new Intent(view.getContext(), Tabs.class);
                                startActivityForResult(cross, TABS);
                            } else {
                                messenger.alert("Failed to create task!");
                            }
                        }
                    } else {
                        messenger.alert("Fields can not be empty!");
                    }
                }
            });
        }
    }


    private ArrayAdapter<ActivityStructure> activityListAdapter() {
        ActivityDataSource activityDataSource = new ActivityDataSource(this);
        List<ActivityStructure> activities = activityDataSource.getAllActivities();
        ArrayAdapter<ActivityStructure> adapter = new ArrayAdapter<ActivityStructure>(this,
                android.R.layout.simple_spinner_item, activities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private long getProjectID() {
        SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
        Session session = new Session(settings);
        long pId = session.getProjectId();
        if (session.getProjectId() == 0) {
            ProjectDataSource source = new ProjectDataSource(this);
            pId = source.getNAProject(session.getUserId()).getId();
        }
        return pId;
    }
}