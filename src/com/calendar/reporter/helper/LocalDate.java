package com.calendar.reporter.helper;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LocalDate {
    private String currentDate;
    private Calendar calendar;

    public LocalDate(String currentDate){
        this.currentDate = currentDate;
        this.calendar = Calendar.getInstance();
    }

    public String incrementByDay(int step) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(currentDate));
        } catch (ParseException e) {
            throw new Error("Error while parsing " + e.getMessage());
        }
        calendar.add(Calendar.DATE, step);
        return dateFormat.format(calendar.getTime());
    }

    public String getDayName(){
        String dayNames[] = new DateFormatSymbols().getWeekdays();
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return  dayNames[dayWeek] + " " + dayWeek;
    }
    public String getMonthName(){
        String monthNames[] = new DateFormatSymbols().getMonths();
        int month = calendar.get(Calendar.MONTH);
        return monthNames[(month-1)];
    }


}