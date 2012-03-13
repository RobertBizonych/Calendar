package com.xtensivearts.episode.nine;

import java.util.ArrayList;
import java.util.Arrays;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class Episode9 extends ListActivity {

	/** We want to display these names as our list items */
	String[] names = {
		"Ashley","Brenda","Charles","Heather","Christine","Debbie","Jordan","Dan","Erin","Eugene","Faith",
		"Perry","Vincent","Frank","Gabrielle","Elliot","Geoffrey","Samuel","Harvey","Ian","Nina","Jessica",
		"John","Kathleen","Keith","Laura","Lloyd","Brad","Marion","Michael","Nathan","Olivia","Carla","Oscar",
		"Paris","Peter","Rachel","Richard","Aaron","Sandra","Taylor","Tyler","Veronica","Isabella","Zoe",
		"William","Christopher","Zachary","Trey","Jacob","Michael","Ethan","Emma","Jayden","Emily","Sophia",
		"Noah","James","Jonathan","Angel","Samantha","Jack","Bob","Chase","Kayla"
	};
	
	private ArrayAdapter<String> adapter;		// The list adapter
	private String selectedItem;				// Stores the selected list item
	private final Context context = this;		// This context
	
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		// Create the listener for normal item clicks
		OnItemClickListener itemListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long rowid) {

				Toast.makeText(
						getApplicationContext(),
						"You have clicked on " + parent.getItemAtPosition(position).toString() + ".",
						Toast.LENGTH_SHORT).show();
			}
		};
		
		// Create the listener for long item clicks
		OnItemLongClickListener itemLongListener = new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long rowid) {
				
				// Store selected item in global variable 
				selectedItem = parent.getItemAtPosition(position).toString();
				
				/** Build new AlertDialog
				 * 
				 * See Episode #8 for details on how to build an AlertDialog
				 * 
				 **/
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Do you want to remove " + selectedItem + "?");
				builder.setCancelable(false);
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						adapter.remove(selectedItem);
						adapter.notifyDataSetChanged();
				
						Toast.makeText(
								getApplicationContext(),
								selectedItem + " has been removed.",
								Toast.LENGTH_SHORT).show();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

				// Create and show the dialog
				builder.show();
				
				// Signal OK to avoid further processing of the long click
				return true;
			}
		};
		
		// Convert array to ArrayList
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(names));
		
		// Create new ArrayAdapter with the ArrayList as underlying data source
		adapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1,
				list);
		
		// Assign adapter to the list
		setListAdapter(adapter);
		
		// Assign both click listeners
		getListView().setOnItemClickListener(itemListener);
		getListView().setOnItemLongClickListener(itemLongListener);
	}
}
