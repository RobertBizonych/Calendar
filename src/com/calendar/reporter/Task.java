package com.calendar.reporter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.calendar.reporter.database.activity.ActivityDataSource;
import com.calendar.reporter.database.activity.ActivityStructure;
import com.calendar.reporter.database.project.ProjectDataSource;
import com.calendar.reporter.database.project.ProjectStructure;
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
        activityList.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ActivityStructure act = (ActivityStructure) parent.getItemAtPosition(position);
                        activity = act;
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        final EditText projectName = (EditText) findViewById(R.id.projectName);
        final EditText projectDescription = (EditText) findViewById(R.id.projectDescription);
        final HourPicker hourPicker = (HourPicker) findViewById(R.id.hourPicker);
        final MinutePicker minutePicker = (MinutePicker) findViewById(R.id.minutePicker);
        final Messenger messenger = new Messenger(this);

        Button createTask = (Button) findViewById(R.id.createTask);
        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameProject = projectName.getText().toString();
                String descriptionProject = projectDescription.getText().toString();

                int pickHour = hourPicker.getValue();
                int pickMinute = minutePicker.getValue();
                int time = pickHour * 60 + pickMinute;

                Calendar currentDate = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat(TaskStructure.DATE_FORMAT);
                String dateNow = formatter.format(currentDate.getTime());

                if (!nameProject.equals("") && !descriptionProject.equals("") && activity != null && !(pickHour == 0 && pickMinute == 0)) {

                    TaskStructure task = dataSource.createTask(nameProject, descriptionProject, dateNow, time, activity.getId(), projectId);
                    if (task != null) {
                        messenger.alert("Task was created " + task.getName());
                        Intent cross = new Intent(view.getContext(), Projects.class);
                        cross.putExtra("session",userId);
                        startActivityForResult(cross, PROJECTS);
                    } else {
                        messenger.alert("Failed!");
                    }
                }
            }
        });
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
        if (pId == 0) {
            ProjectDataSource source = new ProjectDataSource(this);
            pId = source.getNAProject().getId();
        }
        return pId;
    }
}