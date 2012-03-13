package com.calendar.reporter.database.activity;

public class ActivityStructure {
    private long id;
    private String name;

    public static final String TABLE_NAME = "activities";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

}
