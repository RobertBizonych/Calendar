package com.calendar.reporter;

import android.app.Activity;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Task extends Activity {
    private ActivityStructure activity;
    private final int PROJECTS = 0;
    private static final int TASKS = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        final TaskDataSource dataSource = new TaskDataSource(this);

        Bundle bundle = getIntent().getExtras();
        final long userId = bundle.getLong("session");
        final long projectId = getProjectID(bundle);

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
            createTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameTask = taskName.getText().toString();
                    String descriptionTask = taskDescription.getText().toString();

                    int pickHour = hourPicker.getValue();
                    int pickMinute = minutePicker.getValue();
                    int time = pickHour * 60 + pickMinute;

                    Calendar currentDate = Calendar.getInstance();
                    SimpleDateFormat formatter = new SimpleDateFormat(TaskStructure.DATE_FORMAT);
                    String dateNow = formatter.format(currentDate.getTime());

                    if (!nameTask.equals("") && !descriptionTask.equals("") && activity != null && !(pickHour == 0 && pickMinute == 0)) {
                        int status = dataSource.updateTask(nameTask, descriptionTask, taskId);
                        if (status == 1) {
                            messenger.alert("Task " + task.getName() + " was updated");
                            crossTask(view, userId, projectId);
                        } else {
                            messenger.alert("Failed to update!");
                        }

                    }
                }
            });
        } else {
            createTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameTask = taskName.getText().toString();
                    String descriptionTask = taskDescription.getText().toString();

                    int pickHour = hourPicker.getValue();
                    int pickMinute = minutePicker.getValue();
                    int time = pickHour * 60 + pickMinute;

                    Calendar currentDate = Calendar.getInstance();
                    SimpleDateFormat formatter = new SimpleDateFormat(TaskStructure.DATE_FORMAT);
                    String dateNow = formatter.format(currentDate.getTime());

                    if (!nameTask.equals("") && !descriptionTask.equals("") && activity != null && !(pickHour == 0 && pickMinute == 0)) {
                        TaskStructure task = dataSource.createTask(nameTask, descriptionTask, dateNow, time, activity.getId(), projectId);
                        if (task != null) {
                            messenger.alert("Task " + task.getName() + " is successfully created");
                            crossTask(view, userId, projectId);
                        } else {
                            messenger.alert("Failed to create task!");
                        }
                    }
                }
            });
        }
    }

    private void crossTask(View view, long userId, long projectId) {
        Intent cross = new Intent(view.getContext(), Tasks.class);
        cross.putExtra("session", userId);
        cross.putExtra("projectId", projectId);
        startActivityForResult(cross, TASKS);
    }

    private ArrayAdapter<ActivityStructure> activityListAdapter() {
        ActivityDataSource activityDataSource = new ActivityDataSource(this);
        List<ActivityStructure> activities = activityDataSource.getAllActivities();
        ArrayAdapter<ActivityStructure> adapter = new ArrayAdapter<ActivityStructure>(this,
                android.R.layout.simple_spinner_item, activities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private long getProjectID(Bundle bundle) {
        long pId = bundle.getLong("projectId");
        long userId = bundle.getLong("session");
        if (pId == 0) {
            ProjectDataSource source = new ProjectDataSource(this);
            pId = source.getNAProject(userId).getId();
        }
        return pId;
    }
}