package com.example.aep_project.pedometerapplication;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.content.Context;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Pedometer extends Activity implements View.OnClickListener {
    //needed for this class
    TextView textView, textView2, textView3, textView4, textView5, textView6;
    Button buttonReset, buttonStop;
    private MediaPlayer sound;

    SensorManager sensorManager;
    float acceleration, x, y, z, g;
    float previousY, currentY, previousX, currentX;
    float previousZ, currentZ;
    int numSteps, StepsDone, bar;
    SeekBar seekBar;
    double threshold;
    boolean stop = false, flag = false;
    double resulta, vbegin, vafter, dist, distafter, stepl, calori, weight;

    //needed for the other activities the class calls
    Button btnAdd;
    Button btnGetAll;
    TextView data_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);

        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);

        buttonReset = (Button) findViewById(R.id.button);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        sound = MediaPlayer.create(Pedometer.this, R.raw.shortbeep);

        seekBar.setProgress(2);
        seekBar.setOnSeekBarChangeListener(seekBarListener);
        threshold = 2.12;
        textView5.setText(String.valueOf(threshold));

        previousY = 0;
        currentY = 0;
        numSteps = 0;

        acceleration = 0.00f;
        enableAccelerometerListening();

        //for the calling activities
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnGetAll = (Button) findViewById(R.id.btnGetAll);

        btnAdd.setOnClickListener(this);
        btnGetAll.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pedometer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enableAccelerometerListening() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            currentZ = z;
            currentY = y;
            currentX = x;

            g = ((x * x + y * y + z * z)) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

            if (g > threshold) {
                numSteps++;
                textView4.setText(String.valueOf(numSteps));
                sound.start();
            }

            textView.setText(String.valueOf(x));
            textView2.setText(String.valueOf(y));
            textView3.setText(String.valueOf(z));
            previousY = y;
            previousX = x;
            previousZ = z;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    //beje int dhe merr hapat
    public void resetSteps(View v) {
        stop = false;
        flag = false;
        resulta = 0;
        vafter = 0;
        vbegin = 0;
        dist = 0;
        distafter = 0;
        StepsDone = numSteps;
        numSteps = 0;
        textView4.setText(String.valueOf(numSteps));

        if (threshold == 1.32) {
            stepl = 0.0003604;
            dist = StepsDone * stepl;
            calori = 0.5 * weight * dist;
        } else {
            if (threshold == 1.72) {
                stepl = 0.0007332;
                dist = StepsDone * stepl;
                calori = 0.5 * weight * dist;
            } else {
                if (threshold == 2.12) {
                    stepl = 0.001044;
                    dist = StepsDone * stepl;
                    calori = 0.5 * weight * dist;
                } else {
                    if (threshold == 2.52) {
                        stepl = 0.001218;
                        dist = StepsDone * stepl;
                        calori = 0.5 * weight * dist;
                    } else {
                        stepl = 0.001553;
                        dist = StepsDone * stepl;
                        calori = 0.5 * weight * dist;
                    }
                }
            }
            textView6.setText(dist + " miles   " + calori + " calories");
        }
    }

    private OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            bar = seekBar.getProgress();
            switch (bar) {
                case 0:
                    threshold = 1.32;
                    textView5.setText(String.valueOf(threshold));
                    break;
                case 1:
                    threshold = 1.72;
                    textView5.setText(String.valueOf(threshold));
                    break;
                case 2:
                    threshold = 2.12;
                    textView5.setText(String.valueOf(threshold));
                    break;
                case 3:
                    threshold = 2.52;
                    textView5.setText(String.valueOf(threshold));
                    break;
                case 4:
                    threshold = 2.92;
                    textView5.setText(String.valueOf(threshold));
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    public double distanceGot() {
        while (!stop) {
            SystemClock.sleep(2);
            resulta = Math.sqrt(x * x + y * y + z * z);
            vafter = vbegin + resulta * 2;
            distafter = dist + vafter * 2;

            dist = distafter;

            stop = flag;
        }
        return distafter;
    }

    public void stopButton(View v) {
        flag = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btnAdd):
                Intent intent1 = new Intent(this, DataDetail.class);
                intent1.putExtra("data_Id", 0);
                startActivity(intent1);
                break;

            case (R.id.btnGetAll):
                Intent intent2 = new Intent(this, DataList.class);
                intent2.putExtra("data_Id", 0);
                startActivity(intent2);
                break;
        }
    }
}
