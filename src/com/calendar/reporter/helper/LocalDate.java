package com.calendar.reporter.helper;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LocalDate {
    private String currentDate;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    public LocalDate(String currentDate) {
        this.currentDate = currentDate;
        this.calendar = Calendar.getInstance();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        changeTime();
    }

    public String increment(int step, int dateType) {
        calendar.add(dateType, step);
        return this.toString();
    }


    public String getDayName() {
        String dayNames[] = new DateFormatSymbols().getWeekdays();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return dayNames[dayOfWeek] + " " + dayOfMonth;
    }

    public String getMonthName() {
        String monthNames[] = new DateFormatSymbols().getMonths();
        int month = calendar.get(Calendar.MONTH);
        return monthNames[month];
    }

    private void changeTime() {
        try {
            calendar.setTime(dateFormat.parse(currentDate));
        } catch (ParseException e) {
            throw new Error("Error while parsing " + e.getMessage());
        }
    }

    @Override
    public String toString(){
        return dateFormat.format(calendar.getTime());
    }

}