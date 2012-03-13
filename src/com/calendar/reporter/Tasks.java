package com.calendar.reporter;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.calendar.reporter.database.task.TaskDataSource;
import com.calendar.reporter.database.task.TaskStructure;

import java.util.List;

public class Tasks extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);

        TaskDataSource dataSource = new TaskDataSource(this);
        Bundle bundle = getIntent().getExtras();
        final long projectId = bundle.getLong("projectId");

        List<TaskStructure> tasks = dataSource.getAllTasks(projectId);
        ArrayAdapter<TaskStructure> adapter = new ArrayAdapter<TaskStructure>(this,
                android.R.layout.simple_list_item_1, tasks);
        setListAdapter(adapter);

    }
}