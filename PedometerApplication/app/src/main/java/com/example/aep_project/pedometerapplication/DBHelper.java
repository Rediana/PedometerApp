package com.example.aep_project.pedometerapplication;

/**
 * Created by AEP-Project on 9/28/2014.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "data.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_DATA = "CREATE TABLE " + Data.TABLE  + "("
                + Data.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Data.KEY_DATE + " TEXT, "
                + Data.KEY_CALORIE + " INTEGER, "
                + Data.KEY_STEP + " INTEGER )";

        db.execSQL(CREATE_TABLE_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE);
        // Create tables again
        onCreate(db);
    }

}
