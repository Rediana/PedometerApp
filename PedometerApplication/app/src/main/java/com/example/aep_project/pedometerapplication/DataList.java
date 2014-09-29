package com.example.aep_project.pedometerapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class DataList extends ListActivity {
    TextView data_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        DataRepo repo = new DataRepo(this);

        ArrayList<HashMap<String, String>> dataList =  repo.getDataList();
        if(dataList.size()!=0) {
            ListView lv = getListView();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    data_Id = (TextView) view.findViewById(R.id.data_Id);
                    String dataId = data_Id.getText().toString();
                    Intent objIndent = new Intent(getApplicationContext(),DataDetail.class);
                    objIndent.putExtra("data_Id", Integer.parseInt( dataId));
                    startActivity(objIndent);
                }
            });
            ListAdapter adapter = new SimpleAdapter( DataList.this, dataList, R.layout.data_view, new String[] { "id","date"}, new int[] {R.id.data_Id, R.id.data_date});
            setListAdapter(adapter);
        }else{
            Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.data_list, menu);
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
}
