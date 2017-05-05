package com.effone.hostismeandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by renesotolira on 21/03/15.
 */
public class SqliteConnection  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DroidRestaurant.db";
    public static final String TABLE_NAME = "OrderClient";
    public static final String TABLE_ORDER = "OrderPlace";
    public static final String BOOKING_HISTORY="booking_history";
    public static final String TABLE_PRICE = "PriceClient";
    private static final int DATABASE_VERSION = 1;

    String sqlCreateTableOrder= "CREATE TABLE OrderClient (_id INTEGER PRIMARY KEY, index_category INTEGER, index_food INTEGER, quantity INTEGER, price TEXT, food_name TEXT,order_id TEXT)";

    String sqlCreateTableTotal= "CREATE TABLE PriceClient (_id INTEGER PRIMARY KEY, total TEXT)";
    String sqlCreateTablePlaceOrder="CREATE TABLE OrderPlace (_id TEXT PRIMARY KEY,table_no INTEGER,rest_name TEXT,time_stamp TEXT,status Text,description TEXT,totalPrice REAL,quantity INTEGER)";
    String getSqlCreateBookHistory="CREATE TABLE booking_history (_id INTEGER,order_id Text,date TEXT,rest_name TEXT,description TEXT,table_no INTEGER,bill_no REAL,bill_ammount Real,status TEXT)";


    public SqliteConnection(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //create the table order
        db.execSQL(sqlCreateTableOrder);
        db.execSQL(sqlCreateTableTotal);
        db.execSQL(sqlCreateTablePlaceOrder);
        db.execSQL(getSqlCreateBookHistory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        throw new UnsupportedOperationException("This method is not implemented yet.");
    }


}