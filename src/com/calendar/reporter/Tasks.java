package com.calendar.reporter;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.calendar.reporter.database.project.ProjectStructure;
import com.calendar.reporter.database.task.TaskDataSource;
import com.calendar.reporter.database.task.TaskStructure;
import com.calendar.reporter.helper.Messenger;

import java.util.List;

public class Tasks extends ListActivity {
    private ArrayAdapter<TaskStructure> adapter;
    private static final int TASK = 0;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);

        TaskDataSource dataSource = new TaskDataSource(this);
        Bundle bundle = getIntent().getExtras();
        final long projectId = bundle.getLong("projectId");
        final long userId = bundle.getLong("session");
        List<TaskStructure> tasks = dataSource.getAllTasks(projectId);
        ArrayAdapter<TaskStructure> adapter = new ArrayAdapter<TaskStructure>(this,
                android.R.layout.simple_list_item_1, tasks);
        setListAdapter(adapter);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TaskStructure task = (TaskStructure) adapterView.getItemAtPosition(i);
                Intent cross = new Intent(view.getContext(), Task.class);
                cross.putExtra("type","edit");
                cross.putExtra("session",userId);
                cross.putExtra("projectId", projectId);
                cross.putExtra("taskId",task.getId());
                startActivityForResult(cross,TASK);
            }
        };
        getListView().setOnItemClickListener(onItemClickListener);
    }
}