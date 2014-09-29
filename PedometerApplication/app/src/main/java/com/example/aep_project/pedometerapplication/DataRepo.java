package com.example.aep_project.pedometerapplication;

/**
 * Created by AEP-Project on 9/28/2014.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class DataRepo {
    private DBHelper dbHelper;

    public DataRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Data data) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Data.KEY_CALORIE, data.calorie);
        values.put(Data.KEY_STEP,data.step);
        values.put(Data.KEY_DATE, data.date);

        // Inserting Row
        long data_Id = db.insert(Data.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) data_Id;
    }

    public void delete(int data_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Data.TABLE, Data.KEY_ID + "= ?", new String[] { String.valueOf(data_Id) });
        db.close(); // Closing database connection
    }

    public void update(Data data) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Data.KEY_CALORIE, data.calorie);
        values.put(Data.KEY_STEP,data.step);
        values.put(Data.KEY_DATE, data.date);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Data.TABLE, values, Data.KEY_ID + "= ?", new String[] { String.valueOf(data.data_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getDataList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Data.KEY_ID + "," +
                Data.KEY_DATE + "," +
                Data.KEY_STEP + "," +
                Data.KEY_CALORIE +
                " FROM " + Data.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("id", cursor.getString(cursor.getColumnIndex(Data.KEY_ID)));
                data.put("date", cursor.getString(cursor.getColumnIndex(Data.KEY_DATE)));
                dataList.add(data);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dataList;
    }

    public Data getDataById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Data.KEY_ID + "," +
                Data.KEY_DATE + "," +
                Data.KEY_STEP + "," +
                Data.KEY_CALORIE +
                " FROM " + Data.TABLE
                + " WHERE " +
                Data.KEY_ID + "=?";
        int iCount =0;
        Data data = new Data();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                data.data_ID =cursor.getInt(cursor.getColumnIndex(Data.KEY_ID));
                data.date =cursor.getString(cursor.getColumnIndex(Data.KEY_DATE));
                data.step  = cursor.getInt(cursor.getColumnIndex(Data.KEY_STEP));
                data.calorie =cursor.getInt(cursor.getColumnIndex(Data.KEY_CALORIE));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return data;
    }
}