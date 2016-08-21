package com.android.connal.planit;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class UserInfoActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

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

        // setting up spinner for budget
        Spinner budget_spinner = (Spinner) findViewById(R.id.budgetSpinner);
        ArrayAdapter<CharSequence> budget_adapter = ArrayAdapter.createFromResource(this,
                R.array.budget_array, android.R.layout.simple_spinner_item);
        budget_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        budget_spinner.setAdapter(budget_adapter);

        // setting up spinner for age
        Spinner age_spinner = (Spinner) findViewById(R.id.ageSpinner);
        ArrayAdapter<CharSequence> age_adapter = ArrayAdapter.createFromResource(this,
                R.array.age_array, android.R.layout.simple_spinner_item);
        age_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age_spinner.setAdapter(age_adapter);
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
