package com.example.aep_project.pedometerapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class mainActivity extends ActionBarActivity implements View.OnClickListener{

    Button pedometer ;
    Button map;
    Button compass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pedometer = (Button)findViewById(R.id.pedometer);
        map = (Button)findViewById(R.id.map);
        compass = (Button)findViewById(R.id.compass);

        pedometer.setOnClickListener(this);
        map.setOnClickListener(this);
        compass.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.pedometer) :
                startPedometer();
                break;
            case (R.id.map) :
                startMap();
                break;
            case (R.id.compass) :
                startCompass();
                break;
        }
    }

    public void startPedometer () {
        Intent intent = new Intent(this, Pedometer.class) ;
        startActivity(intent);
    }

    public void startMap(){}

    public void startCompass() {}
}


