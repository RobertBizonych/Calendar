package com.calendar.reporter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.calendar.reporter.database.task.TaskDataSource;
import com.calendar.reporter.database.task.TaskStructure;
import com.calendar.reporter.helper.DateListing;
import com.calendar.reporter.helper.LocalDate;
import com.calendar.reporter.helper.Session;

import java.util.List;

public class Tasks extends ListActivity {
    private static final int TASK = 0;
    private static final int PROJECTS = 0;
    static final private int MENU_ITEM = Menu.FIRST;
    private final Context context = this;
    private static final int TASK_VIEW = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks);

        SharedPreferences settings = getSharedPreferences(Session.PREFS_NAME, 0);
        final Session session = new Session(settings);

        LocalDate localDate = new LocalDate(session.getDate(Session.RELEVANT));

        TextView lowerText = (TextView) findViewById(R.id.lowerTextRelevant);
        lowerText.setText(localDate.getDayName());

        TextView upperText = (TextView) findViewById(R.id.upperTextRelevant);
        upperText.setText(localDate.getMonthName());

        TextView launchText = (TextView) findViewById(android.R.id.empty);

        final TaskDataSource dataSource = new TaskDataSource(this);
        List<TaskStructure> tasks = dataSource.getAllTasks(session.getProjectId(), session.getDate(Session.RELEVANT));
        final ArrayAdapter<TaskStructure> adapter = new ArrayAdapter<TaskStructure>(this,android.R.layout.simple_list_item_1, tasks);
        setListAdapter(adapter);

        DateListing dateListing = new DateListing(this, session, Session.RELEVANT);
        dateListing.upperRightBehavior(R.id.upperRightRelevant);
        dateListing.upperLeftBehavior(R.id.upperLeftRelevant);
        dateListing.lowerRightBehavior(R.id.lowerRightRelevant);
        dateListing.lowerLeftBehavior(R.id.lowerLeftRelevant);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final TaskStructure task = (TaskStructure) adapterView.getItemAtPosition(i);
                Intent go = new Intent(view.getContext(), TaskView.class);
                go.putExtra("taskId", task.getId());
                startActivityForResult(go, TASK_VIEW);
            }
        };

        AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                final TaskStructure task = (TaskStructure) adapterView.getItemAtPosition(i);
                final CharSequence[] items = {"Create new task", "Edit","Delete"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose the action:");
                
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        switch (item) {
                            case 0:
                                Intent cross = new Intent(view.getContext(), Task.class);
                                cross.putExtra("type", "create new task");
                                startActivityForResult(cross,TASK);
                                break;
                            case 1:
                                Intent cross1 = new Intent(view.getContext(), Task.class);
                                cross1.putExtra("type", "edit");
                                cross1.putExtra("taskId", task.getId());
                                startActivityForResult(cross1, TASK);
                                break;
                            case 2:
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                                alertBuilder.setMessage("Do you want to remove " + task.getName() + "?");
                                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        adapter.remove(task);
                                        dataSource.deleteTask(task.getId());
                                        adapter.notifyDataSetChanged();

                                        Toast.makeText(getApplicationContext(), task.getName() +
                                                " has been removed.", Toast.LENGTH_LONG).show();
                                    }
                                });
                                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertBuilder.show();
                                break;
                        }
                    }
                });
                builder.show();
                return true;
            }
        };
        getListView().setOnItemLongClickListener(onItemLongClickListener);
        getListView().setOnItemClickListener(onItemClickListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent cross = new Intent(getApplicationContext(), Projects.class);
            startActivityForResult(cross, PROJECTS);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
// Group ID
        int groupId = 0;
// Unique menu item identifier. Used for event handling.
        int menuItemId = MENU_ITEM;
// The order position of the item
        int menuItemOrder = Menu.NONE;
// Text to be displayed for this menu item.
        int menuItemText = R.string.createTask;
// Create the menu item and keep a reference to it.
        MenuItem menuItem = menu.add(groupId, menuItemId, menuItemOrder, menuItemText);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent cross = new Intent(getApplicationContext(), Task.class);
                cross.putExtra("type", "create new task");
                startActivityForResult(cross, TASK);
                return true;
            }
        });
        return true;
    }
}