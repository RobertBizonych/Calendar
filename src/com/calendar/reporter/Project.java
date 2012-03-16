package com.calendar.reporter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.calendar.reporter.database.project.ProjectDataSource;
import com.calendar.reporter.database.project.ProjectStructure;
import com.calendar.reporter.helper.Messenger;
import com.calendar.reporter.helper.Session;

public class Project extends Activity {
    private ProjectDataSource dataSource;
    private static final int PROJECTS = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project);

        Bundle bundle = getIntent().getExtras();
        SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
        final Session session = new Session(settings);
        dataSource = new ProjectDataSource(this);
        final EditText projectName = (EditText) findViewById(R.id.projectName);
        final EditText projectDescription = (EditText) findViewById(R.id.projectDescription);
        final Messenger messenger = new Messenger(this, Register.class.getName());
        final String type = bundle.getString("type");
        Button createButton = (Button) findViewById(R.id.createProject);

        if (type != null && type.equals("edit")) {
            createButton.setText("Update");
            final ProjectStructure project = dataSource.getProject(session.getProjectId());
            projectName.setText(project.getName());
            projectDescription.setText(project.getDescription());
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameProject = projectName.getText().toString();
                    String descriptionProject = projectDescription.getText().toString();
                    String message = "";
                    if (!nameProject.equals("") && !descriptionProject.equals("")) {
                        int status = dataSource.updateProject(nameProject, descriptionProject, session.getProjectId());
                        if (status == 1) {
                            message = project.getName() + " is successfully updated";
                        } else {
                            message = project.getName() + " is failed while update";
                        }
                        crossProject(view, session.getUserId());
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
                    if (dataSource.projectExist(nameProject)) {
                        message = "This name '" + nameProject + "' is reserved!";
                    } else {
                        if (!nameProject.equals("") && !descriptionProject.equals("")) {
                            ProjectStructure project = dataSource.createProject(nameProject, descriptionProject, session.getUserId());
                            if (project != null) {
                                message = project.getName() + " is successfully created";
                            } else {
                                message = "Failed while create";
                            }

                            crossProject(view, session.getUserId());
                        } else {
                            message = "Fields can not be empty";
                        }
                    }
                    messenger.alert(message);
                }
            });

        }
    }

    private void crossProject(View view, long userId) {
        Intent cross = new Intent(view.getContext(), Projects.class);
        cross.putExtra("session", userId);
        startActivityForResult(cross, PROJECTS);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent cross = new Intent(getApplicationContext(), Projects.class);
            startActivityForResult(cross, PROJECTS);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}