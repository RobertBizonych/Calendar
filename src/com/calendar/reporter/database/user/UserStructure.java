package com.calendar.reporter.database.user;

public class UserStructure {
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASS = "password";

    private long id;
    private String nickname;
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return "Nickname = " + nickname;
    }
}
