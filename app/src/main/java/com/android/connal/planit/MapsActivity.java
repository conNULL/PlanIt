package com.android.connal.planit;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private SeekBar seekBar;
    private TextView textView;
    private GoogleMap mMap;
    private double lon;
    private double lat;
    private double rad;
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        extras = intent.getExtras();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initializeVariables();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                rad = seekBar.getProgress()*10;
                textView.setText("Radius: " + rad + "km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void initializeVariables() {
        seekBar = (SeekBar)findViewById(R.id.radiusSeekBar);
        textView = (TextView) findViewById(R.id.radiusSeekBarLabel);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-79.38,43.65)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(-79.38,43.65)));
    }

    public void newlocationtextclicked(View view){
        EditText location = (EditText)findViewById(R.id.newlocation);
        location.setText("");
    }

    public void newlocationbuttonclicked(View view) {
        EditText location_tf = (EditText)findViewById(R.id.newlocation);
        String location = location_tf.getText().toString();
        List<android.location.Address> addressList = null;

        if(location != null || location.equals("")){
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mMap.clear();
            android.location.Address address = addressList.get(0);
            lat = address.getLatitude();
            lon = address.getLongitude();
            LatLng latLng = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    public double[] info(){
        double[] i = {lon,lat,rad};
        TextView textView1 = (TextView)findViewById(R.id.newlocation);
        return i;
    }

    public void sendFullInfo(View view) {
        Intent intent = new Intent(this, FsqActivity.class);

        // bundle more information
        extras.putDouble("EXTRA_LAT", lat);
        extras.putDouble("EXTRA_LON", lon);
        extras.putDouble("EXTRA_RAD", rad);

        intent.putExtras(extras);
        startActivity(intent);
    }
}
