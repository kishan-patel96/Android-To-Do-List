package com.example.kishan.todolist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> titles;
    ArrayList<String> descriptions;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("To Do List");
        centerTitle();

        titles = new ArrayList<>();
        descriptions = new ArrayList<>();

        final EditText taskTitle = findViewById(R.id.tasktitle);
        final EditText taskDescription = findViewById(R.id.taskdescription);

        final ListView listView = findViewById(R.id.mylist);
        customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(MainActivity.this, "Reminders Cleared!", Toast.LENGTH_SHORT).show();
                        titles.clear();
                        descriptions.clear();
                        customAdapter.notifyDataSetChanged();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        final Button add = findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(taskTitle.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Empty Title!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(taskDescription.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Reminder Added!", Toast.LENGTH_SHORT).show();
                    titles.add(taskTitle.getText().toString());
                    descriptions.add("");
                    customAdapter.notifyDataSetChanged();

                    taskTitle.setText("");
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Reminder Added!", Toast.LENGTH_SHORT).show();
                    titles.add(taskTitle.getText().toString());
                    descriptions.add(taskDescription.getText().toString());
                    customAdapter.notifyDataSetChanged();

                    taskTitle.setText("");
                    taskDescription.setText("");
                }
            }
        });

        final Button clear = findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setMessage("Remove all reminders?") ;
                alert.setPositiveButton("Yes", dialogClickListener);
                alert.setNegativeButton("No", dialogClickListener);
                alert.show();
            }
        });

        taskDescription.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            add.performClick();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(MainActivity.this, "Reminder Removed!", Toast.LENGTH_SHORT).show();
                titles.remove(position);
                descriptions.remove(position);
                customAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    private void centerTitle() {
        ArrayList<View> textViews = new ArrayList<>();

        getWindow().getDecorView().findViewsWithText(textViews, getTitle(), View.FIND_VIEWS_WITH_TEXT);

        if(textViews.size() > 0) {
            AppCompatTextView appCompatTextView = null;
            if(textViews.size() == 1) {
                appCompatTextView = (AppCompatTextView) textViews.get(0);
            } else {
                for(View v : textViews) {
                    if(v.getParent() instanceof Toolbar) {
                        appCompatTextView = (AppCompatTextView) v;
                        break;
                    }
                }
            }

            if(appCompatTextView != null) {
                ViewGroup.LayoutParams params = appCompatTextView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                appCompatTextView.setLayoutParams(params);
                appCompatTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
    }

    public class CustomAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.activity_listview, null);

            TextView title = convertView.findViewById(R.id.tasktitle);
            TextView description = convertView.findViewById(R.id.taskdescription);

            title.setClickable(false);
            title.setFocusable(false);
            description.setClickable(false);
            description.setFocusable(false);

            title.setText(titles.get(position));
            description.setText(descriptions.get(position));

            title.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(MainActivity.this, "Reminder " + (position + 1) + " Removed!", Toast.LENGTH_SHORT).show();
                    titles.remove(position);
                    descriptions.remove(position);
                    customAdapter.notifyDataSetChanged();

                    return true;
                }
            });

            description.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(MainActivity.this, "Reminder " + (position + 1) + " Removed!", Toast.LENGTH_SHORT).show();
                    titles.remove(position);
                    descriptions.remove(position);
                    customAdapter.notifyDataSetChanged();

                    return true;
                }
            });

            return convertView;
        }
    }
}
