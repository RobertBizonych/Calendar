package com.calendar.reporter;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.calendar.reporter.database.task.TaskDataSource;
import com.calendar.reporter.database.task.TaskStructure;
import com.calendar.reporter.helper.Session;

import java.util.List;

public class Tasks extends ListActivity {
    private ArrayAdapter<TaskStructure> adapter;
    private static final int TASK = 0;
    private static final int PROJECTS = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);

        SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
        final Session session = new Session(settings);

        TaskDataSource dataSource = new TaskDataSource(this);
        List<TaskStructure> tasks = dataSource.getAllTasks(session.getProjectId());
        ArrayAdapter<TaskStructure> adapter = new ArrayAdapter<TaskStructure>(this,
                android.R.layout.simple_list_item_1, tasks);
        setListAdapter(adapter);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TaskStructure task = (TaskStructure) adapterView.getItemAtPosition(i);
                Intent cross = new Intent(view.getContext(), Task.class);
                cross.putExtra("type","edit");
                cross.putExtra("taskId",task.getId());
                startActivityForResult(cross,TASK);
            }
        };
        getListView().setOnItemClickListener(onItemClickListener);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent cross = new Intent(getApplicationContext(), Projects.class);
            startActivityForResult(cross, PROJECTS);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}