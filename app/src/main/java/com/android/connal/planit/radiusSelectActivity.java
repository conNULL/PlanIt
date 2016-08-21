package com.android.connal.planit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class radiusSelectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radius_select);

        // Draw the radius circle and slider
        setContentView(new radiusSelectCustomView(this));
    }
}


