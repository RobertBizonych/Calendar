package com.calendar.reporter.database.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.calendar.reporter.database.DataBaseHelper;


public class UserDataSource{
    private DataBaseHelper dbHelper;

    private String[] allColumns = {UserStructure.COLUMN_ID,
            UserStructure.COLUMN_NAME, UserStructure.COLUMN_PASS};

    public UserDataSource(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public UserStructure createUser(String name, String password, String confirmation) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            if (password.equals(confirmation)) {
                ContentValues values = new ContentValues();
                values.put(UserStructure.COLUMN_NAME, name);

                values.put(UserStructure.COLUMN_PASS, password);
                long insertId = database.insert(UserStructure.TABLE_NAME, null, values);

                if (insertId < 0) {
                    Log.w(UserDataSource.class.getName(), "Error occurred while inserting new value in the table!");
                    return null;
                } else {
                    Cursor cursor = database.query(UserStructure.TABLE_NAME,
                            allColumns, UserStructure.COLUMN_ID + " = " + insertId, null,
                            null, null, null);
                    return cursorToUser(cursor);
                }
            } else {
//            messenger.error(UserDataSource.class.getName(), "Handle this situation!");
                return null;
            }
        } finally {
            database.close();
        }
    }

    private UserStructure cursorToUser(Cursor cursor) {
        if (cursor.moveToFirst()) {
            UserStructure userStructure = new UserStructure();
            userStructure.setId(cursor.getLong(0));
            userStructure.setNickname(cursor.getString(1));
            userStructure.setPassword(cursor.getString(2));
            return userStructure;
        } else {
            return null;
        }

    }

    public UserStructure Login(String username, String password) throws android.database.SQLException {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + UserStructure.TABLE_NAME + " WHERE name=? AND password=?",
                    new String[]{username, password});
            if (cursor != null) {
                if (cursor.getCount() > 0)
                    return cursorToUser(cursor);
            }
            return null;
        } finally {
            database.close();
        }
    }

    public boolean userExists(String nickname) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try{
            String whereSequence = "name = '" + nickname + "'";
            Cursor cursor = database.query(UserStructure.TABLE_NAME,
                    allColumns, whereSequence, null, null, null, null);

            UserStructure user = null;
            if(cursor.moveToFirst()){
                user = cursorToUser(cursor);
            }
            return user != null;
        }finally {
            database.close();
        }
    }
}
