package com.calendar.reporter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.calendar.reporter.database.project.ProjectDataSource;
import com.calendar.reporter.database.project.ProjectStructure;
import com.calendar.reporter.helper.Messenger;

public class Project extends Activity {
    private ProjectDataSource dataSource;
    private static final int PROJECTS = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project);

        Bundle bundle = getIntent().getExtras();
        dataSource = new ProjectDataSource(this);
        final EditText projectName = (EditText) findViewById(R.id.projectName);
        final EditText projectDescription = (EditText) findViewById(R.id.projectDescription);
        final Messenger messenger = new Messenger(this, Register.class.getName());
        final long userId = bundle.getLong("session");
        final long projectId = bundle.getLong("projectId");
        final String type = bundle.getString("type");
        Button createButton = (Button) findViewById(R.id.createProject);

        if (type!= null && type.equals("edit")) {
            createButton.setText("Update");
            final ProjectStructure project = dataSource.getProject(projectId);
            projectName.setText(project.getName());
            projectDescription.setText(project.getDescription());
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameProject = projectName.getText().toString();
                    String descriptionProject = projectDescription.getText().toString();
                    String message = "";
                    if (!nameProject.equals("") && !descriptionProject.equals("")) {
                        int status = dataSource.updateProject(nameProject, descriptionProject, projectId);
                        if (status == 1) {
                            message = project.getName() + " is successfully updated";
                        } else {
                            message = project.getName() + " is failed while update";
                        }
                        Intent cross = new Intent(view.getContext(), Projects.class);
                        cross.putExtra("session", userId);
                        startActivityForResult(cross, PROJECTS);
                    } else {
                        message = "Fields can not be empty";
                    }
                    messenger.alert(message);
                }
            });
        } else {

            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameProject = projectName.getText().toString();
                    String descriptionProject = projectDescription.getText().toString();
                    String message = "";

                    if (!nameProject.equals("") && !descriptionProject.equals("")) {
                        ProjectStructure project = dataSource.createProject(nameProject, descriptionProject, userId);
                        if (project != null) {
                            message = project.getName() + " is successfully created";
                        } else {
                            message = "Failed while create";
                        }

                        Intent cross = new Intent(view.getContext(), Projects.class);
                        cross.putExtra("session", userId);
                        startActivityForResult(cross, PROJECTS);
                    } else {
                        message = "Fields can not be empty";
                    }
                    messenger.alert(message);
                }
            });

        }
    }


}