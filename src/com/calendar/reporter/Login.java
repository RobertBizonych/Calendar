package com.calendar.reporter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.calendar.reporter.database.user.UserDataSource;
import com.calendar.reporter.database.user.UserStructure;
import com.calendar.reporter.helper.Messenger;
import com.calendar.reporter.helper.Session;

public class Login extends Activity {
    static final private int REGISTER = 0;
    static final private int PROJECT = 0;
    private static final int ABOUT = 0;
    static final private int MENU_ITEM = Menu.FIRST;
    private TextToSpeech talker;

    class MyOnInitListener implements TextToSpeech.OnInitListener{
        @Override
        public void onInit(int status) {
            talker.speak("Welcome to the reporter!", TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        talker = new TextToSpeech(getApplicationContext(), new MyOnInitListener());

        final MediaPlayer mediaPlayer = MediaPlayer.create(Login.this, R.raw.curve);
        mediaPlayer.start();
        mediaPlayer.setVolume(2,2);
        SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
        final Session session = new Session(settings);
        session.reset();

        Button registerButton = (Button) findViewById(R.id.registerButton);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        final EditText userName = (EditText) findViewById(R.id.usernameField);
        final EditText userPassword = (EditText) findViewById(R.id.passwordField);
        final Messenger messenger = new Messenger(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent cross = new Intent(view.getContext(), com.calendar.reporter.Register.class);
                startActivityForResult(cross, REGISTER);
                mediaPlayer.stop();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = userName.getText().toString();
                String password = userPassword.getText().toString();
                try {
                    if (username.equals("") && password.equals("")) {
                        messenger.alert("Username and Password should not be empty.");
                    } else {
                        if (username.length() > 0 && password.length() > 0) {
                            UserDataSource dbUser = new UserDataSource(Login.this);
                            UserStructure user = dbUser.Login(username, password);
                            if (user == null) {
                                messenger.alert("Invalid Username/Password");
                            } else {
                                messenger.alert("Successfully Logged In  " + user.getNickname());
                                session.setUserId(user.getId());
                                Intent intent = new Intent(v.getContext(), Projects.class);
                                intent.putExtra("session", user.getId());
                                startActivityForResult(intent, PROJECT);
                                mediaPlayer.stop();
                            }
                        }
                    }
                } catch (Exception e) {
                    messenger.alert(e.getMessage());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        int groupId = 0;
        int menuItemId = MENU_ITEM;
        int menuItemOrder = Menu.NONE;
        int menuItemText = R.string.about_title;
        MenuItem menuItem = menu.add(groupId, menuItemId,
                menuItemOrder, menuItemText);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent cross = new Intent(getApplicationContext(), About.class);
                startActivityForResult(cross, ABOUT);
                return true;
            }
        });
        return true;
    }



}