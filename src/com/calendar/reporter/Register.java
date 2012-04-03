package com.calendar.reporter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.calendar.reporter.database.project.ProjectDataSource;
import com.calendar.reporter.database.project.ProjectStructure;
import com.calendar.reporter.database.user.UserDataSource;
import com.calendar.reporter.database.user.UserStructure;
import com.calendar.reporter.helper.Messenger;

public class Register extends Activity {
    private UserDataSource dataSource;
    static final private int LOGIN = 0;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        final UserDataSource dataSource = new UserDataSource(this);
        final ProjectDataSource source = new ProjectDataSource(this);

        Button registerButton = (Button) findViewById(R.id.rgr);
        final Messenger messenger = new Messenger(this, Register.class.getName());

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextView nameField = (TextView) findViewById(R.id.name);
                TextView passField = (TextView) findViewById(R.id.pass);
                TextView confirmField = (TextView) findViewById(R.id.confirm);

                String nickname = nameField.getText().toString();
                String password = passField.getText().toString();
                String confirm = confirmField.getText().toString();
                String message = "";
                UserStructure userStructure = new UserStructure();
                if (!password.equals("") && !nickname.equals("") && !confirm.equals("") && !nickname.equals(userStructure.getNickname())) {
                    if (password.equals(confirm)) {
                        if (dataSource.userExists(nickname)) {
                            messenger.alert("User with nickname '" + nickname + "' already exist!" );
                        } else {
                            UserStructure user = dataSource.createUser(nickname, password, confirm);
                            ProjectStructure project = source.createProject("N/A", "description", user.getId());

                            if (user != null && project != null) {
                                message = user.getNickname() + ", you are successfully registered!";
                            } else {
                                message = "Failed to register!";
                            }
                            messenger.alert(message);

                            Intent cross = new Intent(view.getContext(), Login.class);
                            startActivityForResult(cross, LOGIN);
                        }
                    } else {
                        messenger.alert("Wrong password confirmation!");
                    }
                    passField.setText("");
                    confirmField.setText("");
                } else {
                    messenger.alert("Fields can not be empty!");
                }
            }
        });
    }
}