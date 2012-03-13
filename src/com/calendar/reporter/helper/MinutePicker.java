package com.calendar.reporter.helper;

import android.content.Context;
import android.util.AttributeSet;

public class MinutePicker extends NumberPicker{
    public MinutePicker(Context context, AttributeSet attributeSet){
        super(context, attributeSet, 0, 59);
    }
}
