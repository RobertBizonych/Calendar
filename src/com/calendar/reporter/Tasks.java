package com.calendar.reporter;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.calendar.reporter.database.task.TaskDataSource;
import com.calendar.reporter.database.task.TaskStructure;
import com.calendar.reporter.helper.Messenger;

import java.util.List;

public class Tasks extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);

        SharedPreferences settings = getSharedPreferences(Login.PREFS_NAME, 0);
        long userId = settings.getLong("session",0);
        long projectId = settings.getLong("projectId",0);

        TaskDataSource dataSource = new TaskDataSource(this);
        List<TaskStructure> tasks = dataSource.getAllTasks(projectId);
        ArrayAdapter<TaskStructure> adapter = new ArrayAdapter<TaskStructure>(this,
                android.R.layout.simple_list_item_1, tasks);
        setListAdapter(adapter);

    }
}