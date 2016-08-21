package com.android.connal.planit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.AttributedCharacterIterator;
import java.util.LinkedList;

public class radiusSelectCustomView extends SurfaceView {

    private Paint paint;
    private SeekBar zoomSlider;
    private TextView zoomSliderText;

    private Canvas canvas;
    private SurfaceHolder sh;
    private boolean created = false;
    public radiusSelectCustomView(Context context) {
        super(context);
        init();
    }

    public radiusSelectCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public radiusSelectCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init(){

        sh = getHolder();



        sh.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                created = true;

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width, int height) {


            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {


            }
        });
    }

    public void draw(){

        if(created){
            canvas = sh.lockCanvas();
            // Initial radius of circle
            int radius = 5;

            int xCenter = (this.getResources().getDisplayMetrics().widthPixels)/2;
            int yCenter = (this.getResources().getDisplayMetrics().heightPixels)/2;

            // drawCircle(xCenter, yCenter, radius, colour)
            canvas.drawCircle(xCenter, yCenter, radius, paint);

            // Initialize the slider and its text
            zoomSlider = (SeekBar) findViewById(R.id.zoomSlider);
            zoomSliderText = (TextView) findViewById(R.id.zoomSliderText);

            // Initialize text
            //zoomSliderText.setText("Searching a radius of " + zoomSlider.getProgress() + " km");

            zoomSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                // Handler for when user moves the slider
                @Override
                public void onProgressChanged(SeekBar zoomSlider, int sliderMovement, boolean isUserInput) {
                  //  radius += sliderMovement;
                }

                // Handler for when user clicks on the slider
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                // Handler for when user lets go of the slider
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }
    }

}
















    /*
    private Paint paint;
    private SeekBar zoomSlider;
    private TextView zoomSliderText;
    private static int radius =0;
    private boolean created = true;

    public radiusSelectCustomView(Context context) {
        super(context);

        paint = new Paint();
        // Do not let the circle be filled (e.g. only an outline)
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
    }

    public radiusSelectCustomView(Context context, AttributeSet attrs) {
        super(context);

        paint = new Paint();
        // Do not let the circle be filled (e.g. only an outline)
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
    }

    public radiusSelectCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context);

        paint = new Paint();
        // Do not let the circle be filled (e.g. only an outline)
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
    }

    // Draws the radius circle
    @Override
    protected void onDraw(Canvas canvas) {
        draw(canvas);
    }

    public void draw(Canvas canvas){

        // Initial radius of circle
        radius = 5;

        int xCenter = (this.getResources().getDisplayMetrics().widthPixels)/2;
        int yCenter = (this.getResources().getDisplayMetrics().heightPixels)/2;

        // drawCircle(xCenter, yCenter, radius, colour)
        canvas.drawCircle(xCenter, yCenter, radius, paint);

        // Initialize the slider and its text
        zoomSlider = (SeekBar) findViewById(R.id.zoomSlider);
        zoomSliderText = (TextView) findViewById(R.id.zoomSliderText);

        // Initialize text
        //zoomSliderText.setText("Searching a radius of " + zoomSlider.getProgress() + " km");

        zoomSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            // Handler for when user moves the slider
            @Override
            public void onProgressChanged(SeekBar zoomSlider, int sliderMovement, boolean isUserInput) {
                radius += sliderMovement;
            }

            // Handler for when user clicks on the slider
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            // Handler for when user lets go of the slider
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

}*/