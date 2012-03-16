package com.calendar.reporter.helper;

import android.content.Context;
import android.util.AttributeSet;


public class HourPicker extends NumberPicker {
    public HourPicker(Context context, AttributeSet attributeSet){
        super(context, attributeSet, 0, 999);
    }
}
