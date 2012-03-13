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

        dataSource = new ProjectDataSource(this);

        Button createButton = (Button) findViewById(R.id.createProject);
        Bundle bundle = getIntent().getExtras();
        final EditText projectName = (EditText) findViewById(R.id.projectName);
        final EditText projectDescription = (EditText) findViewById(R.id.projectDescription);
        final Messenger messenger = new Messenger(this, Register.class.getName());
        final long userId = bundle.getLong("session");

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameProject = projectName.getText().toString();
                String descriptionProject = projectDescription.getText().toString();
                if (projectName != null && projectDescription != null) {
                    ProjectStructure project = dataSource.createProject(nameProject, descriptionProject, userId);
                    messenger.alert(project.getName() + " is successfully created");

                    Intent cross = new Intent(view.getContext(), Projects.class);
                    cross.putExtra("session", userId);
                    startActivityForResult(cross, PROJECTS);
                } else {
                    messenger.alert("Fields can not be empty");
                }
            }
        });
    }


}