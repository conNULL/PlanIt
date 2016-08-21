package com.android.connal.planit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // the following code displays the information collected from the last intent
        // only meant for testing

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String pplNum = extras.getString("EXTRA_PPL");
        String budget = extras.getString("EXTRA_BUD");
        int day = extras.getInt("EXTRA_DAY");
        int month = extras.getInt("EXTRA_MON");
        int year = extras.getInt("EXTRA_YEAR");
        int hour = extras.getInt("EXTRA_HOUR");
        int min = extras.getInt("EXTRA_MIN");
        String age = extras.getString("EXTRA_AGE");
        double lat = extras.getDouble("EXTRA_LAT");
        double lon = extras.getDouble("EXTRA_LON");
        double rad = extras.getDouble("EXTRA_RAD");
        String message = new StringBuilder().append(pplNum).append("\n").append(budget).append("\n")
                .append(day).append("/").append(month).append("/").append(year).append("\n").append(hour)
                .append(":").append(min).append("\n").append(age).append("\n").append(lat).append("\n")
                .append(lon).append("\n").append(rad).append("\n").toString();
        TextView textView = new TextView(this);
        textView.setTextSize(32);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }
}
