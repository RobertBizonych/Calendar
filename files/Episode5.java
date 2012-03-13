package com.xtensivearts.episode.five;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TextView;

public class Episode5 extends Activity {

	/** Global variables */
	OnClickListener radioListener;
	TextView tvPick;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Create a new OnClickListener for the radio buttons
        radioListener = new OnClickListener() {

			/** This gets called each time a radio button is clicked */
        	@Override
			public void onClick(View v) {
        		
        		/* The parameter v actually holds the View object that has been clicked on.
        		 * If we cast that parameter to a RadioButton, we simply get the radio
        		 * button that has been clicked on.
        		 */
        		RadioButton rb = (RadioButton)v;
				
				/* Check which radio button was clicked by calling rb.getId()
				 * and comparing the result with the id defined in R.id
				 */
        		switch(rb.getId())
        		{
        			case R.id.radio_red: tvPick.setBackgroundColor(Color.parseColor("#ff0000")); break;
        			case R.id.radio_green: tvPick.setBackgroundColor(Color.parseColor("#00ff00")); break;
        			case R.id.radio_blue: tvPick.setBackgroundColor(Color.parseColor("#0000ff")); break;
        			case R.id.radio_gray: tvPick.setBackgroundColor(Color.parseColor("#666666")); break;
        		}
			}
        	
        };
        
        // Assign all elements from the layout to their respective variables
        tvPick = (TextView)findViewById(R.id.text_pick);
        RadioButton rbRed = (RadioButton)findViewById(R.id.radio_red);
        rbRed.setOnClickListener(radioListener);
        RadioButton rbGreen = (RadioButton)findViewById(R.id.radio_green);
        rbGreen.setOnClickListener(radioListener);
        RadioButton rbBlue = (RadioButton)findViewById(R.id.radio_blue);
        rbBlue.setOnClickListener(radioListener);
        RadioButton rbGray = (RadioButton)findViewById(R.id.radio_gray);
        rbGray.setOnClickListener(radioListener);
    }
}