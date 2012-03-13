package com.calendar.reporter.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.calendar.reporter.helper.Messenger;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "reporter.db" ;
    private static final int DATABASE_VERSION = 5;

    private Messenger messenger;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.messenger = new Messenger(DataBaseHelper.class.getName());
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, password TEXT NOT NULL);");
        database.execSQL("CREATE TABLE projects (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, description TEXT NOT NULL, user_id INTEGER);");
        database.execSQL("CREATE TABLE tasks (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, " +
                "description TEXT NOT NULL, time INTEGER, date DATE, project_id INTEGER, activity_id INTEGER);");

        database.execSQL("CREATE TABLE activities (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL);");
        database.execSQL("INSERT INTO `activities` (`name`) VALUES ('Self development');");
        database.execSQL("INSERT INTO `activities` (`name`) VALUES ('Working time');");
        database.execSQL("INSERT INTO `activities` (`name`) VALUES ('Extra time');");
        database.execSQL("INSERT INTO `activities` (`name`) VALUES ('Team time');");

        database.execSQL("INSERT INTO `users` (`name`,`password`) VALUES ('1','1');");
        database.execSQL("INSERT INTO `projects` (`name`,`description`,`user_id`) VALUES ('1 project','description','1');");
        database.execSQL("INSERT INTO `projects` (`name`,`description`,`user_id`) VALUES ('2 project','description','1');");
        database.execSQL("INSERT INTO `projects` (`name`,`description`,`user_id`) VALUES ('N/A','description','1');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        messenger.debug("Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS tasks");
        db.execSQL("DROP TABLE IF EXISTS activities");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS projects");
        onCreate(db);
    }

}