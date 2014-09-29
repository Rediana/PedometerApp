package com.example.aep_project.pedometerapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DataDetail extends ActionBarActivity implements android.view.View.OnClickListener{

    Button btnSave;
    Button btnDelete;
    Button btnClose;
    EditText editDate;
    EditText editStep;
    EditText editCalorie;
    private int _data_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_detail);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editDate = (EditText) findViewById(R.id.editDate);
        editStep = (EditText) findViewById(R.id.editStep);
        editCalorie = (EditText) findViewById(R.id.editCalorie);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _data_Id =0;
        Intent intent = getIntent();
        _data_Id =intent.getIntExtra("data_Id", 0);
        DataRepo repo = new DataRepo(this);
        Data data = new Data();
        data = repo.getDataById(_data_Id);

        editCalorie.setText(String.valueOf(data.calorie));
        editDate.setText(data.date);
        editStep.setText(String.valueOf(data.step));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.data_detail, menu);
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

    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            DataRepo repo = new DataRepo(this);
            Data data = new Data();
            data.calorie = Integer.parseInt(editCalorie.getText().toString());
            data.step = Integer.parseInt(editStep.getText().toString());
            data.date = editDate.getText().toString();
            data.data_ID=_data_Id;

            if (_data_Id==0){
                _data_Id = repo.insert(data);

                Toast.makeText(this,"New Record Insert",Toast.LENGTH_SHORT).show();
            }else{
                repo.update(data);
                Toast.makeText(this,"Record updated",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btnDelete)){
            DataRepo repo = new DataRepo(this);
            repo.delete(_data_Id);
            Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT);
            finish();
        }else if (view== findViewById(R.id.btnClose)){
            finish();
        }
    }
}
