package com.calendar.reporter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.calendar.reporter.database.user.UserDataSource;
import com.calendar.reporter.database.user.UserStructure;
import com.calendar.reporter.helper.Messenger;
import com.calendar.reporter.helper.Session;

public class Login extends Activity {
    static final private int REGISTER = 0;
    static final private int PROJECT = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        final EditText userName = (EditText) findViewById(R.id.usernameField);
        final EditText userPassword = (EditText) findViewById(R.id.passwordField);
        final Messenger messenger = new Messenger(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent cross = new Intent(view.getContext(), com.calendar.reporter.Register.class);
                startActivityForResult(cross, REGISTER);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = userName.getText().toString();
                String password = userPassword.getText().toString();
                try {
                    if (username.length() > 0 && password.length() > 0) {
                        UserDataSource dbUser = new UserDataSource(Login.this);
                        UserStructure user = dbUser.Login(username, password);
                        if (user == null) {
                            messenger.alert("Invalid Username/Password");
                        } else {
                            messenger.alert("Successfully Logged In  " + user.getNickname());

                            SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
                            Session session = new Session(settings);
                            session.setUserId(user.getId());


                            Intent intent = new Intent(v.getContext(), Projects.class);
                            intent.putExtra("session", user.getId());
                            startActivityForResult(intent, PROJECT);
                        }
                    }
                } catch (Exception e) {
                    messenger.alert(e.getMessage());
                }
            }
        });


    }

//    public void onBackPressed() {
//        if (){
//            super.onBackPressed();
//        }else{

    //        }
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //Ask the user if they want to quit
//            new AlertDialog.Builder(this)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .setTitle("Exit")
//                    .setMessage("Are you sure you want to leave?")
//                    .setNegativeButton(android.R.string.cancel, null)
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
            Messenger messenger = new Messenger(Login.class.getName());
            // Exit the activity
            Intent goToA = new Intent(getApplicationContext(), Login.class);
            goToA.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(goToA);
            onDestroy();
            Login.this.finish();
            messenger.error("Hijo de puta");
        }
        // })
        //  .show();
        // Say that we've consumed the event
            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    }
}
