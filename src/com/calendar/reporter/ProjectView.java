package com.calendar.reporter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import com.calendar.reporter.database.project.ProjectDataSource;
import com.calendar.reporter.database.project.ProjectStructure;
import com.calendar.reporter.helper.Session;

/**
 * Created by IntelliJ IDEA.
 * Date: 03.04.12
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
public class ProjectView extends Activity {
    private ProjectDataSource dataSource;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_view);
        SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
        final Session session = new Session(settings);
        dataSource = new ProjectDataSource(this);

        TextView projectName = (TextView) findViewById(R.id.projectName);
        TextView projectDescription = (TextView) findViewById(R.id.projectDescription);

        ProjectStructure projectStructure = dataSource.getProject(session.getProjectId());
        projectName.setText("Name: " + projectStructure.getName());
        projectDescription.setText("Description: " + projectStructure.getDescription());
    }
}