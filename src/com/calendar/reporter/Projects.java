package com.calendar.reporter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.calendar.reporter.database.project.ProjectDataSource;
import com.calendar.reporter.database.project.ProjectStructure;

import java.util.List;

public class Projects extends ListActivity {
    private ProjectDataSource dataSource;
    private static final int PROJECT = 0;
    private static final int TASKS = 0;
    private final Context context = this;
    private String selectedItem;
    private ArrayAdapter<ProjectStructure> adapter;

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

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProjectStructure project = (ProjectStructure) adapterView.getItemAtPosition(i);
                Intent cross = new Intent(view.getContext(), Tasks.class);
                cross.putExtra("projectId", project.getId());
                startActivityForResult(cross,TASKS);
            }
        };
        AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ProjectStructure project = (ProjectStructure) adapterView.getItemAtPosition(i);
                selectedItem = project.toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove " + selectedItem + "?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(project);
                        dataSource.deleteProject(project.getId());
                        adapter.notifyDataSetChanged();

                        Toast.makeText(getApplicationContext(),selectedItem +
                                " has been removed.",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Create and show the dialog
                builder.show();

                // Signal OK to avoid further processing of the long click
                return true;

            }
        };
        adapter = new ArrayAdapter<ProjectStructure>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(onItemClickListener);
        getListView().setOnItemLongClickListener(onItemLongClickListener);

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