package com.sndo9.robert.gaslog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PopupWindow popUp;
    private TextView mTextMessage;
    private Button addEntry;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popUp = new PopupWindow(this);


        addEntry = findViewById(R.id.add_entry_button);

        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("entry", "New entry clicked");
                Log.d("entry", String.valueOf(R.id.new_entry_popup));

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.new_entry, null);
                popUp = new PopupWindow(view, -2, -2);
                popUp.setElevation(5.0f);

                popUp.showAtLocation(findViewById(R.id.log_display), Gravity.CENTER, 0,0);
            }
        });

        //Populate Entry list
        EntryAdaptor entryAdaptor = new EntryAdaptor(this, new ArrayList<LogEntry>());
        ListView entryView = (ListView) findViewById(R.id.entry_view);
        entryView.setAdapter(entryAdaptor);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Log.d("JSON:", "Starting...");

        try {
            JSONObject obj = new JSONObject(loadJson("gasbook.log"));
            Log.d("JSON", obj.toString());
            JSONArray logArray = obj.getJSONArray("Gas Log");
            Log.d("JSON", logArray.toString());

            for (int i = 0; i < logArray.length(); i++) {
                JSONObject entryLine = logArray.getJSONObject(i);
                Log.d("JSON " + i, entryLine.toString());

                LogEntry entry = new LogEntry(entryLine);

                if(entry.wasSuccessful()) {
                    entryAdaptor.add(entry);

                } else {
                    Log.d("JSON", "Entry " + i + " failed");
                }
            }

        } catch(JSONException e) {
            e.printStackTrace();
        }

        Log.d("JSON", " ");
    }

    private TextView createView(JSONObject entry, String name) {
        TextView view;
        try{
            view = new TextView(this);
            view.setText(entry.get(name).toString());
            view.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT, 1f));
        } catch(JSONException e) {
            e.printStackTrace();
            view = null;
        }

        return view;
    }

    private String loadJson(String jsonFile) {
        String output = null;

        try{
            InputStream log = this.getAssets().open(jsonFile);
            int bytes = log.available();
            byte[] buffer = new byte[bytes];
            log.read(buffer);
            log.close();
            output = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
