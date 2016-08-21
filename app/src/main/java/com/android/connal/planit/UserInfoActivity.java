package com.android.connal.planit;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.EditText;

import java.util.Calendar;

public class UserInfoActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private TextView reviewView;
    private int numPeople;
    private String budget;
    private int year, month, day;
    private int hour, minutes;
    private String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        dateView = (TextView) findViewById(R.id.dateText);
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minutes = minute;

                boolean status = updateReview(numPeople, budget, year, month, day, hour, minutes, age);
            }
        });

        final Spinner budget_spinner = (Spinner) findViewById(R.id.budgetSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout

        // setting up spinner for budget
        Spinner budget_spinner = (Spinner) findViewById(R.id.budgetSpinner);

        ArrayAdapter<CharSequence> budget_adapter = ArrayAdapter.createFromResource(this,
                R.array.budget_array, android.R.layout.simple_spinner_item);
        budget_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        budget_spinner.setAdapter(budget_adapter);


        // Listen to user's budget selection based on spinner input -> update
        budget_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                budget = budget_spinner.getSelectedItem().toString();
                boolean status = updateReview(numPeople, budget, year, month, day, hour, minutes, age);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });


        final Spinner age_spinner = (Spinner) findViewById(R.id.ageSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout

        // setting up spinner for age
        Spinner age_spinner = (Spinner) findViewById(R.id.ageSpinner);

        ArrayAdapter<CharSequence> age_adapter = ArrayAdapter.createFromResource(this,
                R.array.age_array, android.R.layout.simple_spinner_item);
        age_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age_spinner.setAdapter(age_adapter);

        // Listen to user's age selection based on spinner input -> update
        age_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                age = age_spinner.getSelectedItem().toString();
                boolean status = updateReview(numPeople, budget, year, month, day, hour, minutes, age);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        // Listen to user input for numPpl -> update
        final EditText pplNumText = (EditText)findViewById(R.id.pplNumText);
        pplNumText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                numPeople = Integer.parseInt(pplNumText.getText().toString());
                boolean status = updateReview(numPeople, budget, year, month, day, hour, minutes, age);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }

    // the four methods below are for implementing the calendar dialogue

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "select date", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private boolean updateReview(int numPeople, String budget, int year, int month, int day, int hour,
                              int minutes, String age) {
        reviewView.setText(new StringBuilder().append(numPeople).append("\n").append(budget).append("\n")
                .append(day).append("/").append(month+1).append("/").append(year).append("\n").append(hour)
                .append(": ").append(minutes).append("\n").append(age).append("years old"));
        return true;
    }

    // Next activity shows generated schedule of events
    public void generatePlans(View view) {
        Intent intent = new Intent(this, ScheduleEventListActivity.class);

    // pass collected information to next activity
    @TargetApi(Build.VERSION_CODES.M)
    public void sendInfo(View view) {
        Intent intent = new Intent(this, MapsActivity.class);

        // get information from the input fields
        EditText editText = (EditText) findViewById(R.id.pplNumText);
        String pplNum = editText.getText().toString();
        Spinner budget_spinner = (Spinner) findViewById(R.id.budgetSpinner);
        String budget = budget_spinner.getSelectedItem().toString();
        TimePicker time_picker = (TimePicker) findViewById(R.id.timePicker);
        int hour = time_picker.getHour();
        int min = time_picker.getMinute();
        Spinner age_spinner = (Spinner) findViewById(R.id.ageSpinner);
        String age = age_spinner.getSelectedItem().toString();

        // bundle the information
        Bundle extras = new Bundle();
        extras.putString("EXTRA_PPL", pplNum);
        extras.putString("EXTRA_BUD", budget);
        extras.putInt("EXTRA_DAY", day);
        extras.putInt("EXTRA_MON", month+1);
        extras.putInt("EXTRA_YEAR", year);
        extras.putInt("EXTRA_HOUR", hour);
        extras.putInt("EXTRA_MIN", min);
        extras.putString("EXTRA_AGE", age);

        intent.putExtras(extras);
        startActivity(intent);
    }
}
