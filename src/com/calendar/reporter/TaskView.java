package com.calendar.reporter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.calendar.reporter.database.task.TaskDataSource;
import com.calendar.reporter.database.task.TaskStructure;

/**
 * Created by IntelliJ IDEA.
 * Date: 03.04.12
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */
public class TaskView extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_view);

        Bundle bundle = getIntent().getExtras();
        final TaskDataSource dataSource = new TaskDataSource(this);
        final long taskId = bundle.getLong("taskId");
        final TaskStructure task = dataSource.getTask(taskId);
        TextView taskName = (TextView) findViewById(R.id.taskName);
        TextView taskDescription = (TextView) findViewById(R.id.taskDescription);
        TextView taskTime = (TextView) findViewById(R.id.taskTime);

        taskName.setText("Name: " + task.getName());
        taskDescription.setText("Description: " + task.getDescription());
        taskTime.setText("Duration: " + task.getTime() + " minutes");
    }
}