package com.calendar.reporter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.calendar.reporter.database.project.ProjectDataSource;
import com.calendar.reporter.database.task.TaskDataSource;
import com.calendar.reporter.database.task.TaskStructure;
import com.calendar.reporter.helper.HourPicker;
import com.calendar.reporter.helper.MinutePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Task extends Activity {
    private TaskDataSource dataSource;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        dataSource = new TaskDataSource(this);

        Bundle bundle = getIntent().getExtras();
        long pId = bundle.getLong("projectId");
        if (pId == 0) {
            ProjectDataSource source = new ProjectDataSource(this);
            pId = source.getNAProject().getId();
        }
        final long projectId = pId;

        final Spinner activityList = (Spinner) findViewById(R.id.activityList);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.activities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityList.setAdapter(adapter);

        final EditText projectName = (EditText) findViewById(R.id.projectName);
        final EditText projectDescription = (EditText) findViewById(R.id.projectDescription);
        final HourPicker hourPicker = (HourPicker) findViewById(R.id.hourPicker);
        final MinutePicker minutePicker = (MinutePicker) findViewById(R.id.minutePicker);

        Button createTask = (Button) findViewById(R.id.createTask);
        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameProject = projectName.getText().toString();
                String descriptionProject = projectDescription.getText().toString();
                String listActivity = activityList.getSelectedItem().toString();

                int pickHour = hourPicker.getValue();
                int pickMinute = minutePicker.getValue();
                int time = pickHour * 60 + pickMinute;

                Calendar currentDate = Calendar.getInstance();
                SimpleDateFormat formatter =
                        new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
                String dateNow = formatter.format(currentDate.getTime());


                if (nameProject != null && descriptionProject != null && listActivity != null && pickHour != 0 && pickMinute != 0) {
//                    TaskStructure task = dataSource.createTask(nameProject, descriptionProject, dateNow, time, 0, projectId);
                }


            }
        });
    }
}