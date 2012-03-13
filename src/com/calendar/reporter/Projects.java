package com.calendar.reporter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.calendar.reporter.database.project.ProjectDataSource;
import com.calendar.reporter.database.project.ProjectStructure;

import java.util.List;

public class Projects extends ListActivity {
    private ProjectDataSource dataSource;
    private static final int PROJECT = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projects);

        dataSource = new ProjectDataSource(Projects.this);
        Bundle bundle = getIntent().getExtras();
        final long userId = bundle.getLong("session");

        Button projectButton = (Button) findViewById(R.id.createProject);
        Button taskButton = (Button) findViewById(R.id.createTask);

        List<ProjectStructure> values = dataSource.getAllProjects(userId);

        ArrayAdapter<ProjectStructure> adapter = new ArrayAdapter<ProjectStructure>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        projectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cross = new Intent(view.getContext(), Project.class);
                cross.putExtra("session", userId);
                startActivityForResult(cross,PROJECT);
            }
        });
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cross = new Intent(view.getContext(), Task.class);
                cross.putExtra("session", userId);
                startActivityForResult(cross,PROJECT);
            }
        });

    }

}